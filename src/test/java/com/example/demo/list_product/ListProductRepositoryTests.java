package com.example.demo.list_product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.product.Product;

import jakarta.validation.ConstraintViolationException;

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
    void shouldPersistListProduct() {
        Product product = new Product("Product 1");
        Integer amount = 2;

        ListProduct listProduct = listProductRepository.saveAndFlush(new ListProduct(product, amount));

        assertNotNull(listProduct.getId());
        assertEquals(product.getId(), listProduct.getId());
        assertEquals(product, listProduct.getProduct());
        assertEquals(amount, listProduct.getAmount());

        ListProduct foundListProduct = listProductRepository.findById(listProduct.getId()).get();
        assertEquals(listProduct, foundListProduct);
    }

    @Test
    void shouldPersistListProductWithExistingProduct() {
        Product product = entityManager.persist(new Product("Product 1"));
        Integer amount = 2;

        assertNotNull(product.getId());

        ListProduct listProduct = listProductRepository.saveAndFlush(new ListProduct(product, amount));

        assertNotNull(listProduct.getId());
        assertEquals(product.getId(), listProduct.getId());
        assertEquals(product, listProduct.getProduct());
        assertEquals(amount, listProduct.getAmount());

        ListProduct foundListProduct = listProductRepository.findById(listProduct.getId()).get();
        assertEquals(listProduct, foundListProduct);
    }

    @Test
    void shouldNotPersistInvalidListProduct() {
        ListProduct listProduct = new ListProduct();
        assertThrows(JpaSystemException.class, () -> listProductRepository.save(listProduct));

        listProduct.setProduct(new Product("Product 1"));
        assertNull(listProduct.getAmount());
        assertThrows(ConstraintViolationException.class, () -> listProductRepository.saveAndFlush(listProduct));

        listProduct.setAmount(-1);
        assertThrows(DataIntegrityViolationException.class, () -> listProductRepository.saveAndFlush(listProduct));
    }
}
