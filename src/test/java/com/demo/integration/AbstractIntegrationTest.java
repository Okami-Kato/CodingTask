package com.demo.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    public static PostgreSQLContainer<?> postgreSQL =
            new PostgreSQLContainer<>("postgres:13")
                    .withUsername("root")
                    .withPassword("root")
                    .withDatabaseName("data_local");

    static {
        postgreSQL.start();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQL::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQL::getUsername);
        registry.add("spring.datasource.username", postgreSQL::getPassword);
    }
}