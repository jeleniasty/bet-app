package com.jeleniasty.betapp.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeleniasty.betapp.features.exceptions.CustomError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component("customAuthenticationEntryPoint")
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint
  implements AuthenticationEntryPoint {

  @Override
  public void commence(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException authException
  ) throws IOException {
    var error = new CustomError(
      HttpStatus.UNAUTHORIZED.value(),
      authException.getMessage(),
      LocalDateTime.now()
    );

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    var mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    mapper.writeValue(response.getOutputStream(), error);
  }
}
