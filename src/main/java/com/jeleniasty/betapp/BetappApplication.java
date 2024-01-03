package com.jeleniasty.betapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class BetappApplication {

  public static void main(String[] args) {
    SpringApplication.run(BetappApplication.class, args);
  }
}
