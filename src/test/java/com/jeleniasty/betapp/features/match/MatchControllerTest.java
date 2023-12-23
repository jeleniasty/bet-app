package com.jeleniasty.betapp.features.match;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.model.CompetitionStage;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.team.TeamDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
@ContextConfiguration(classes = MatchController.class)
@ExtendWith(MockitoExtension.class)
class MatchControllerTest {

  @MockBean
  private MatchService matchService;

  @Autowired
  private MatchController matchController;

  private final ObjectMapper objectMapper = new ObjectMapper()
    .registerModule(new JavaTimeModule());

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(matchController).build();
  }

  @Test
  void getUpcomingMatch() throws Exception {
    //arrange
    var matchId = 2137L;
    var matchDTO = createMatchDTO(matchId);

    when(matchService.getMatch(matchId)).thenReturn(matchDTO);

    var expectedResponseBody = objectMapper.writeValueAsString(matchDTO);

    //act
    var responseBody = mockMvc
      .perform(
        MockMvcRequestBuilders
          .get("/match/" + matchId)
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andReturn();

    //assert
    assertThat(responseBody.getResponse().getStatus())
      .isEqualTo(HttpStatus.OK.value());
    assertThat(responseBody.getResponse().getContentAsString())
      .isEqualTo(expectedResponseBody);
  }

  @Test
  void getUpcomingMatches_should_return_10_matches() {
    //arrange
    var matches = List.of(
      createMatchDTO(1),
      createMatchDTO(2),
      createMatchDTO(3),
      createMatchDTO(4),
      createMatchDTO(5),
      createMatchDTO(6),
      createMatchDTO(7),
      createMatchDTO(8),
      createMatchDTO(9),
      createMatchDTO(10),
      createMatchDTO(11)
    );
    //TODO resolve issue with creating UpcomingMatchDTO instance for testing purposes
  }

  private MatchDTO createMatchDTO(long matchId) {
    return new MatchDTO(
      matchId,
      new TeamDTO(
        2137L,
        "Frosinone Calcio",
        "FRO",
        "https://crests.football-data.org/470.png"
      ),
      new TeamDTO(
        52L,
        "Genoa CFC",
        "GEN",
        "https://crests.football-data.org/107.svg"
      ),
      1.0f,
      1.0f,
      1.0f,
      MatchStatus.SCHEDULED,
      CompetitionStage.REGULAR_SEASON,
      null,
      LocalDateTime.parse("2023-11-26T14:00"),
      null,
      444378L
    );
  }
}
