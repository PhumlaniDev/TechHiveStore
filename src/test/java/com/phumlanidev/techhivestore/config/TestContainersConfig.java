package com.phumlanidev.techhivestore.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Test configuration class for setting up Testcontainers with PostgreSQL.
 */
@TestConfiguration
public class TestContainersConfig {

  /**
   * Creates and starts a PostgreSQL container for testing purposes.
   *
   * @return a started PostgreSQLContainer instance
   */
  @Bean
  public PostgreSQLContainer<?> postgreSqlContainer() {
    PostgreSQLContainer<?> postgreSqlContainer = new PostgreSQLContainer<>("postgres:16.0");
    postgreSqlContainer.start();
    return postgreSqlContainer;
  }
}
