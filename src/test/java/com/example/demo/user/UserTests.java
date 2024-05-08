package com.example.demo.user;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.TransactionSystemException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class UserTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldCreateUser() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        User user = new User(name, email);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    @Test
    void shouldNotPersistUserWithBlankProperties() {
        assertThrows(TransactionSystemException.class, () -> userRepository.save(new User()));
        assertThrows(TransactionSystemException.class, () -> userRepository.save(new User("", "")));
    }
}
