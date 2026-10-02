package com.svlogic.opoppr.api;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("e2e")
@EnabledIfSystemProperty(named = "run.e2e", matches = "true")
@Testcontainers
class OpopprApiE2ETest {
    @Container
    static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4")
            .withDatabaseName("opoppr")
            .withUsername("opoppr")
            .withPassword("opoppr");

    @Test
    void migratedDatabaseContainerIsReachable() {
        assertThat(mysql.isRunning()).isTrue();
        assertThat(mysql.getJdbcUrl()).contains("jdbc:mysql://");
    }
}