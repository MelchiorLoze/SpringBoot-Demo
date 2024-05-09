package com.example.demo.list_product;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;

@Testcontainers
@SpringBootTest
class ListProductTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ListProductRepository listProductRepository;

    @Test
    void shouldCreateProduct() {
        Product product = new Product("Product 1");
        product = productRepository.save(product);
        assertNotNull(product.getId());
        Integer amount = 2;
        ListProduct listProduct = new ListProduct(product, amount);
        listProductRepository.save(listProduct);
    }
}
