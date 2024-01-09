package com.jeleniasty.betapp.features.competition;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.jeleniasty.betapp.features.exceptions.CompetitionNotFoundException;
import com.jeleniasty.betapp.features.exceptions.GlobalExceptionHandler;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.model.CompetitionStage;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.result.Duration;
import com.jeleniasty.betapp.features.result.ResultDTO;
import com.jeleniasty.betapp.features.result.Winner;
import com.jeleniasty.betapp.features.result.score.ScoreDTO;
import com.jeleniasty.betapp.features.team.TeamDTO;
import java.time.LocalDate;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = CompetitionController.class)
class CompetitionControllerTest {

  @MockBean
  private CompetitionService competitionService;

  @Autowired
  private CompetitionController competitionController;

  private final ObjectMapper objectMapper = new ObjectMapper()
    .registerModule(new JavaTimeModule());

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc =
      MockMvcBuilders
        .standaloneSetup(competitionController)
        .setControllerAdvice(GlobalExceptionHandler.class)
        .build();
  }

  @Test
  void createNewCompetition_for_available_competition_should_return_response_with_201_status_and_CompetitionDTO_body()
    throws Exception {
    //arrange
    var match1 = new MatchDTO(
      493L,
      new TeamDTO(
        51L,
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
      MatchStatus.FINISHED,
      CompetitionStage.REGULAR_SEASON,
      null,
      LocalDateTime.parse("2023-11-26T14:00"),
      new ResultDTO(
        Winner.HOME_TEAM,
        Duration.REGULAR,
        new ScoreDTO(1, 1),
        null,
        null,
        null,
        new ScoreDTO(2, 1)
      ),
      444378L
    );

    var match2 = new MatchDTO(
      591L,
      new TeamDTO(
        54L,
        "AC Monza",
        "MON",
        "https://crests.football-data.org/5911.png"
      ),
      new TeamDTO(
        55L,
        "AS Roma",
        "ROM",
        "https://crests.football-data.org/100.svg"
      ),
      1.0f,
      1.0f,
      1.0f,
      MatchStatus.SCHEDULED,
      CompetitionStage.REGULAR_SEASON,
      null,
      LocalDateTime.parse("2024-03-03T00:00"),
      null,
      444520L
    );
    var match3 = new MatchDTO(
      690L,
      new TeamDTO(
        56L,
        "US Salernitana 1919",
        "SAL",
        "https://crests.football-data.org/455.png"
      ),
      new TeamDTO(
        1L,
        "AC Milan",
        "MIL",
        "https://crests.football-data.org/98.svg"
      ),
      1.0f,
      1.0f,
      1.0f,
      MatchStatus.TIMED,
      CompetitionStage.REGULAR_SEASON,
      null,
      LocalDateTime.parse("2023-12-22T19:45"),
      null,
      444421L
    );
    var competitionDTO = new CompetitionDTO(
      3L,
      "Serie A",
      "SA",
      CompetitionType.LEAGUE,
      2023,
      "https://crests.football-data.org/SA.png",
      LocalDate.parse("2023-08-19"),
      LocalDate.parse("2024-05-26"),
      List.of(match1, match2, match3)
    );

    CompetitionRequest competitonRequest = new CompetitionRequest("SA", 2023);

    Mockito
      .when(competitionService.createNewCompetition(competitonRequest))
      .thenReturn(competitionDTO);

    String expectedResponseBody = objectMapper.writeValueAsString(
      competitionDTO
    );

    //act
    MvcResult result = mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/competition")
          .content(objectMapper.writeValueAsString(competitonRequest))
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andReturn();

    //assert
    assertThat(result.getResponse().getStatus())
      .isEqualTo(HttpStatus.CREATED.value());
    assertThat(result.getResponse().getContentAsString())
      .isEqualTo(expectedResponseBody);
  }

  @Test
  void createNewCompetition_for_not_available_competition_should_return_response_with_404_status_and_error_message()
    throws Exception {
    //arrange
    var competitonRequest = new CompetitionRequest("SA", 2023);
    var competitionNotFoundException = new CompetitionNotFoundException(
      competitonRequest.code(),
      competitonRequest.season()
    );

    Mockito
      .when(competitionService.createNewCompetition(competitonRequest))
      .thenThrow(competitionNotFoundException);

    //act
    var actualResponse = mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/competition")
          .content(objectMapper.writeValueAsString(competitonRequest))
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andReturn();

    //assert

    var actualResponseMessage = JsonPath.read(
      actualResponse.getResponse().getContentAsString(),
      "$.message"
    );
    assertThat(actualResponse.getResponse().getStatus())
      .isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(actualResponseMessage)
      .isEqualTo(competitionNotFoundException.getMessage());
  }
}
