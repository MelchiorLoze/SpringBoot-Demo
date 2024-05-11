package com.example.demo.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

class UserTests {

    @Test
    void shouldReturnUserAsString() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        User user = new User(name, email);
        assertEquals(String.format("User(id=null, name=%s, email=%s)", name, email), user.toString());
    }

    @Test
    void shouldReturnUserHashCode() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        User user1 = new User(name, email);
        User user2 = new User(name, email);

        assertNotNull(user1.hashCode());
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
