package com.example.demo.product;

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
class ProductRepositoryTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    ProductRepository productRepository;

    @Test
    void shouldPersistProduct() {
        String name = "Product 1";
        Product product = productRepository.saveAndFlush(new Product(name));

        assertNotNull(product.getId());
        assertEquals(name, product.getName());

        Product foundProduct = productRepository.findById(product.getId()).get();
        assertEquals(product, foundProduct);
    }

    @Test
    void shouldNotPersistProductWithBlankProperties() {
        assertThrows(ConstraintViolationException.class, () -> productRepository.saveAndFlush(new Product()));
        assertThrows(ConstraintViolationException.class, () -> productRepository.saveAndFlush(new Product("")));
        assertThrows(ConstraintViolationException.class, () -> productRepository.saveAndFlush(new Product(" ")));
    }
}
