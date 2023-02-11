package com.example.demo.domain;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.UserEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
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

    @SpyBean
    UserRepository userRepository;

    @Captor
    ArgumentCaptor<UserEntity> userEntityCaptor;

    @Test
    void getAllUsers_shouldNotBeEmpty() {
        List<User> users = userService.getAllUsers();

        assertThat(users).isNotEmpty();
    }

    @Test
    void getUserById_shouldReturnUser() throws ResourceNotFoundException {
        User user = userService.findById(UUID.fromString("e0a084b3-c9f8-4d22-b988-29561a4961c1"));

        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("Skip");
        assertThat(user.getLastName()).isEqualTo("Hovert");
        assertThat(user.getEmail()).isEqualTo("chovert2@economist.com");
    }

    @Test
    void createUser_shouldCreateUser() {
        User user = getValidUser();

        userService.createUser(user);

        verify(userRepository).save(userEntityCaptor.capture());

        UserEntity userEntity = userEntityCaptor.getValue();
        assertThat(userEntity).isNotNull();
        assertThat(userEntity).usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "createdBy", "updatedAt", "updatedBy")
                .isEqualTo(user);

    }

    private User getValidUser() {
        User user = new User();
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        user.setEmail("email@address.com");
        return user;
    }

}
