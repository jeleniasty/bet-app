package com.jeleniasty.betapp.features.bet;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeleniasty.betapp.features.result.Duration;
import com.jeleniasty.betapp.features.result.ResultDTO;
import com.jeleniasty.betapp.features.result.Winner;
import com.jeleniasty.betapp.features.result.score.ScoreDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = BetController.class)
class BetControllerTest {

  @MockBean
  private BetService betService;

  @Autowired
  private BetController betController;

  private final ObjectMapper objectMapper = new ObjectMapper()
    .registerModule(new JavaTimeModule());
  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(betController).build();
  }

  @Test
  void createBet_should_return_201code_and_created_BetDTO() throws Exception {
    //arrange
    var creteBetDTO = new CreateBetDTO(
      new ResultDTO(
        Winner.DRAW,
        Duration.REGULAR,
        null,
        null,
        null,
        null,
        null
      ),
      BetType.FULL_TIME_RESULT,
      2137L
    );

    var betDTO = new BetDTO(
      1L,
      creteBetDTO.matchId(),
      creteBetDTO.betType(),
      creteBetDTO.resultDTO(),
      LocalDateTime.now()
    );
    Mockito.when(betService.createBet(creteBetDTO)).thenReturn(betDTO);

    //act
    var result = mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/bet")
          .content(objectMapper.writeValueAsString(creteBetDTO))
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andReturn();

    //assert
    assertThat(result.getResponse().getStatus())
      .isEqualTo(HttpStatus.CREATED.value());
    assertThat(result.getResponse().getContentAsString())
      .isEqualTo(objectMapper.writeValueAsString(betDTO));
  }

  @Test
  void getCurrentUserBets_should_return_200code_and_listOfBets()
    throws Exception {
    //arrange
    var matchId = 1L;
    var betDTO1 = new BetDTO(
      2L,
      324L,
      BetType.CORRECT_SCORE,
      new ResultDTO(
        Winner.AWAY_TEAM,
        Duration.REGULAR,
        new ScoreDTO(1, 1),
        null,
        null,
        null,
        new ScoreDTO(1, 2)
      ),
      LocalDateTime.now()
    );
    var betDTO2 = new BetDTO(
      2L,
      324L,
      BetType.FULL_TIME_RESULT,
      new ResultDTO(
        Winner.DRAW,
        Duration.REGULAR,
        null,
        null,
        null,
        null,
        null
      ),
      LocalDateTime.now()
    );
    var betDTO3 = new BetDTO(
      3L,
      2344L,
      BetType.CORRECT_SCORE,
      new ResultDTO(
        Winner.DRAW,
        Duration.REGULAR,
        new ScoreDTO(1, 1),
        null,
        null,
        null,
        new ScoreDTO(1, 1)
      ),
      LocalDateTime.now()
    );

    var expectedResponse = List.of(betDTO1, betDTO2, betDTO3);
    Mockito
      .when(betService.getCurrentUserBets(matchId))
      .thenReturn(expectedResponse);

    //act
    var result = mockMvc
      .perform(
        MockMvcRequestBuilders
          .get("/bets/user/" + matchId)
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andReturn();

    //assert
    assertThat(result.getResponse().getStatus())
      .isEqualTo(HttpStatus.OK.value());
    assertThat(result.getResponse().getContentAsString())
      .isEqualTo(objectMapper.writeValueAsString(expectedResponse));
  }
  //TODO refactor arrange section to be more readable
}
