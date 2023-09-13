package com.jeleniasty.betapp;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class BetappApplicationBaseTest {

  public static PostgreSQLContainer<?> postgreSQLContainer =
    new PostgreSQLContainer<>("postgres:latest")
      .withUsername("postgres")
      .withPassword("password")
      .withDatabaseName("postgres");

  @BeforeAll
  public static void initializeDbTestcontainerBeforeTest() {
    postgreSQLContainer.start();
  }

  @DynamicPropertySource
  public static void containerConfig(DynamicPropertyRegistry registry) {
    registry.add(
      "spring.datasource.username",
      postgreSQLContainer::getUsername
    );
    registry.add(
      "spring.datasource.password",
      postgreSQLContainer::getPassword
    );
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);

    registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl);
  }
}
