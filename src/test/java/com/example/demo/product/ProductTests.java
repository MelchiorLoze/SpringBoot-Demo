package com.example.demo.product;

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
class ProductTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    ProductRepository productRepository;

    @Test
    void shouldCreateProduct() {
        String name = "Product 1";
        Product product = new Product(name);
        assertEquals(name, product.getName());
    }

    @Test
    void shouldNotPersistProductWithBlankProperties() {
        assertThrows(TransactionSystemException.class, () -> productRepository.save(new Product()));
        assertThrows(TransactionSystemException.class, () -> productRepository.save(new Product("")));
        assertThrows(TransactionSystemException.class, () -> productRepository.save(new Product(" ")));
    }
}
