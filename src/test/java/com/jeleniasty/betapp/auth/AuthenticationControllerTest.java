package com.jeleniasty.betapp.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.jeleniasty.betapp.features.exceptions.CustomError;
import com.jeleniasty.betapp.features.exceptions.GlobalExceptionHandler;
import com.jeleniasty.betapp.features.user.role.RoleName;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest
@ContextConfiguration(
  classes = { AuthenticationController.class, GlobalExceptionHandler.class }
)
class AuthenticationControllerTest {

  @MockBean
  private AuthenticationService authenticationService;

  @Autowired
  private AuthenticationController authenticationController;

  private final ObjectMapper objectMapper = new ObjectMapper()
    .registerModule(new JavaTimeModule());
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc =
      MockMvcBuilders
        .standaloneSetup(authenticationController)
        .setControllerAdvice(GlobalExceptionHandler.class)
        .build();
  }

  @Test
  void register_with_valid_request_body_should_return_201_status_and_authentication_token()
    throws Exception {
    //arrange
    var request = new RegisterRequest(
      "testuser",
      "testemail@email.com",
      "password",
      Set.of(RoleName.PLAYER)
    );

    var expectedResponse = new AuthResponse(
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    );

    Mockito
      .when(authenticationService.register(request))
      .thenReturn(expectedResponse);

    //act
    var actualResponse = mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/api/register")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request))
      )
      .andReturn();

    //assert
    assertThat(actualResponse.getResponse().getStatus())
      .isEqualTo(HttpStatus.CREATED.value());
    assertThat(actualResponse.getResponse().getContentAsString())
      .isEqualTo(objectMapper.writeValueAsString(expectedResponse));
  }

  @Test
  void login_with_valid_credentials_should_return_200_status_and_authentication_token()
    throws Exception {
    //arrange
    var request = new AuthRequest("testemail@gmail.com", "password");

    var expectedResponse = new AuthResponse(
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    );

    Mockito
      .when(authenticationService.authenticate(request))
      .thenReturn(expectedResponse);

    //act
    var result = mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/api/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request))
      )
      .andReturn();

    //assert
    assertThat(result.getResponse().getStatus())
      .isEqualTo(HttpStatus.OK.value());
    assertThat(result.getResponse().getContentAsString())
      .isEqualTo(objectMapper.writeValueAsString(expectedResponse));
  }

  @Test
  void login_with_bad_credentials_should_return_401_status_with_message()
    throws Exception {
    //arrange
    var request = new AuthRequest("testemail@gmail.com", "password");

    var expectedTime = LocalDateTime.now();
    var expectedResponse = new CustomError(
      HttpStatus.UNAUTHORIZED.value(),
      "Bad credentials",
      expectedTime
    );

    Mockito
      .when(authenticationService.authenticate(request))
      .thenThrow(new BadCredentialsException("Bad credentials"));

    //act
    var result = mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/api/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request))
      )
      .andReturn();

    //assert
    var actualStatus = JsonPath.read(
      result.getResponse().getContentAsString(),
      "$.status"
    );
    var actualMessage = JsonPath.read(
      result.getResponse().getContentAsString(),
      "$.message"
    );

    assertThat(result.getResponse().getStatus())
      .isEqualTo(HttpStatus.UNAUTHORIZED.value());
    assertThat(actualStatus).isEqualTo(expectedResponse.getStatus());
    assertThat(actualMessage).isEqualTo(expectedResponse.getMessage());
  }
}
