package com.example.demo.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.validation.ConstraintViolationException;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldPersistUser() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        User user = userRepository.saveAndFlush(new User(name, email));

        assertNotNull(user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());

        User foundUser = userRepository.findById(user.getId()).get();
        assertEquals(user, foundUser);
    }

    @Test
    void shouldNotPersistUserWithBlankProperties() {
        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(new User()));
        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(new User("", "")));
        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(new User(" ", " ")));
    }

    @Test
    void shouldNotPersistUserWithInvalidEmail() {
        assertThrows(ConstraintViolationException.class,
                () -> userRepository.saveAndFlush(new User("John Doe", "john.doe")));
        assertThrows(ConstraintViolationException.class,
                () -> userRepository.saveAndFlush(new User("John Doe", "john doe@example.com")));
    }
}
