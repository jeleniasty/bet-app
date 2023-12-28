package com.jeleniasty.betapp.features.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeleniasty.betapp.features.user.dto.UserScoreDTO;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
@ContextConfiguration(classes = BetappUserController.class)
class BetappUserControllerTest {

  @MockBean
  private BetappUserService betappUserService;

  @Autowired
  private BetappUserController betappUserController;

  private final ObjectMapper objectMapper = new ObjectMapper()
    .registerModule(new JavaTimeModule());
  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(betappUserController).build();
  }

  @Test
  void getUserScores_should_return_list_of_user_scores_with_status_200()
    throws Exception {
    //arrange
    var playerScores = List.of(
      new UserScoreDTO(1L, "testuser1", 11.43),
      new UserScoreDTO(2L, "testuser2", 33.00),
      new UserScoreDTO(3L, "testuser3", 0),
      new UserScoreDTO(4L, "testuser4", 100)
    );

    Mockito.when(betappUserService.getPlayerScores()).thenReturn(playerScores);

    //act
    var result = mockMvc
      .perform(
        MockMvcRequestBuilders
          .get("/user-scores")
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andReturn();

    //assert
    assertThat(result.getResponse().getStatus())
      .isEqualTo(HttpStatus.OK.value());
    assertThat(result.getResponse().getContentAsString())
      .isEqualTo(objectMapper.writeValueAsString(playerScores));
  }

  @Test
  void getUserScores_when_db_is_empty_should_return_empty_list_with_status_200()
    throws Exception {
    //arrange
    List<UserScoreDTO> playerScores = Collections.emptyList();

    Mockito.when(betappUserService.getPlayerScores()).thenReturn(playerScores);

    //act
    var result = mockMvc
      .perform(
        MockMvcRequestBuilders
          .get("/user-scores")
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andReturn();

    //assert
    assertThat(result.getResponse().getStatus())
      .isEqualTo(HttpStatus.OK.value());
    assertThat(result.getResponse().getContentAsString())
      .isEqualTo(objectMapper.writeValueAsString(playerScores));
  }
}
