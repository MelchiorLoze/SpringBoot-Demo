package com.example.demo.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;

class UserTests {

    @Test
    void shouldCompareUsers() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        User user1 = new User(name, email);
        User user2 = new User(name, email);
        assertEquals(user1, user1);
        assertEquals(user1, user2);

        user2.setName("Jane Doe");
        assertNotEquals(user1, user2);

        user2.setName(name);
        user2.setEmail("jane.doe@example.com");
        assertNotEquals(user1, user2);
    }

    @Test
    void shouldReturnUserAsString() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        User user = new User(name, email);
        assertEquals(String.format("User(id=null, name=%s, email=%s, listProducts=null)", name, email),
                user.toString());
    }

    @Test
    void shouldReturnUserHashCode() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        User user1 = new User(name, email);
        User user2 = new User(name, email);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
