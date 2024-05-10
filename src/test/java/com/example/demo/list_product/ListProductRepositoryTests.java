package com.example.demo.list_product;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.product.Product;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ListProductRepositoryTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ListProductRepository listProductRepository;

    @Test
    void shouldSaveListProduct() {
        Product product = entityManager.persist(new Product("Product 1"));
        Integer amount = 2;

        ListProduct listProduct = listProductRepository.save(new ListProduct(product, amount));

        assertEquals(product.getId(), listProduct.getId());
        assertEquals(product, listProduct.getProduct());
        assertEquals(amount, listProduct.getAmount());
    }
}
