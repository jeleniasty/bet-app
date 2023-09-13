package com.jeleniasty.betapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BetappApplication {

  public static void main(String[] args) {
    SpringApplication.run(BetappApplication.class, args);
  }
}
