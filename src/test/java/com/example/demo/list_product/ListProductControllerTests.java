package com.example.demo.list_product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListProductControllerTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ListProductRepository listProductRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        listProductRepository.deleteAll();
    }

    /*
     * GET
     */

    @Test
    void shouldGetAllListProducts() {
    }

    /*
     * POST
     */

    /*
     * PUT
     */

    /*
     * DELETE
     */
}
