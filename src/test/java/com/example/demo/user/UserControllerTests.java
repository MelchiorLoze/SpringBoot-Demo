package com.example.demo.user;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    /*
     * GET
     */

    @Test
    void shouldGetAllUsers() {
        List<User> expectedUsers = userRepository.saveAll(
                List.of(new User("John Doe", "john.doe@example.com"), new User("Jane Doe", "jane.doe@example.com")));

        ResponseEntity<User[]> response = restTemplate.getForEntity("/users", User[].class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<User> actualUsers = List.of(response.getBody());
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void shouldGetUserById() {
        User expectedUser = userRepository.save(new User("John Doe", "john.doe@example.com"));

        ResponseEntity<User> response = restTemplate.getForEntity("/users/{id}", User.class, expectedUser.getId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        User actualUser = response.getBody();
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void shouldValidateUserIdToGet() {
        ResponseEntity<User> response = restTemplate.getForEntity("/users/{id}", User.class, "invalid");
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldNotFindInexistentUser() {
        ResponseEntity<User> response = restTemplate.getForEntity("/users/{id}", User.class, 1);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    /*
     * POST
     */

    @Test
    void shouldCreateUser() {
        User user = new User("John Doe", "john.doe@example.com");

        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        User createdUser = response.getBody();
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());

        Optional<User> actualUser = userRepository.findById(createdUser.getId());
        assertTrue(actualUser.isPresent());
        assertEquals(createdUser, actualUser.get());
    }

    @Test
    void shouldValidateUserToCreate() {
        ResponseEntity<User> response = restTemplate.postForEntity("/users", new User(), User.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * PUT
     */

    @Test
    void shouldUpdateUser() {
        User user = userRepository.save(new User("John Doe", "john.doe@example.com"));

        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");

        ResponseEntity<User> response = restTemplate.exchange("/users/{id}", HttpMethod.PUT, new HttpEntity<>(user),
                User.class, user.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User updatedUser = response.getBody();
        assertEquals(user.getName(), updatedUser.getName());
        assertEquals(user.getEmail(), updatedUser.getEmail());

        Optional<User> actualUser = userRepository.findById(updatedUser.getId());
        assertTrue(actualUser.isPresent());
        assertEquals(updatedUser, actualUser.get());
    }

    @Test
    void shouldCreateUserIfNotExists() {
        User user = new User("John Doe", "john.doe@example.com");

        ResponseEntity<User> response = restTemplate.exchange("/users/{id}", HttpMethod.PUT, new HttpEntity<>(user),
                User.class, 1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        User createdUser = response.getBody();
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());

        Optional<User> actualUser = userRepository.findById(createdUser.getId());
        assertTrue(actualUser.isPresent());
        assertEquals(createdUser, actualUser.get());
    }

    @Test
    void shouldValidateUserIdToUpdate() {
        ResponseEntity<User> response = restTemplate.exchange("/users/{id}", HttpMethod.PUT,
                new HttpEntity<>(new User("John Doe", "john.doe@example.com")), User.class, "invalid");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldValidateUserToUpdate() {
        ResponseEntity<User> response = restTemplate.exchange("/users/{id}", HttpMethod.PUT, null, User.class, 1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        response = restTemplate.exchange("/users/{id}", HttpMethod.PUT, new HttpEntity<>(new User()), User.class, 1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * DELETE
     */

    @Test
    void shouldDeleteUser() {
        User user = userRepository.save(new User("John Doe", "john.doe@example.com"));

        ResponseEntity<Void> response = restTemplate.exchange("/users/{id}", HttpMethod.DELETE, null, Void.class,
                user.getId());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Optional<User> actualUser = userRepository.findById(user.getId());
        assertTrue(actualUser.isEmpty());
    }

    @Test
    void shouldValidateUserIdToDelete() {
        ResponseEntity<Void> response = restTemplate.exchange("/users/{id}", HttpMethod.DELETE, null, Void.class,
                "invalid");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldNotDeleteInexistentUser() {
        ResponseEntity<Void> response = restTemplate.exchange("/users/{id}", HttpMethod.DELETE, null, Void.class, 1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
