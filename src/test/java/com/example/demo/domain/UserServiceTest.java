package com.example.demo.domain;

import com.example.demo.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class UserServiceTest {

    @Container
    static MSSQLServerContainer<?> container = new MSSQLServerContainer<>("mcr.microsoft.com/mssql/server:2019-latest")
            .acceptLicense();

    @DynamicPropertySource
    static void registerMSSQLProperties(DynamicPropertyRegistry registry) {
        container.start();
        await().until(container::isRunning);

        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @AfterAll
    static void teardown() {
        container.stop();
    }

    @Autowired
    UserService userService;

    @Test
    void getAllUsers_doitRetournerTousLesUsers() {
        List<User> users = userService.getAllUsers();

        assertThat(users).isNotEmpty();
    }

}
