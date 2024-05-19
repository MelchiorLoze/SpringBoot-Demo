package com.example.demo.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

class ProductTests {

    @Test
    void shouldCompareProducts() {
        String name = "Product 1";
        Product product1 = new Product(name);
        Product product2 = new Product(name);
        assertEquals(product1, product1);
        assertEquals(product1, product2);
        product2.setName("Product 2");
        assertNotEquals(product1, product2);
    }

    @Test
    void shouldReturnProductAsString() {
        String name = "Product 1";
        Product product = new Product(name);
        assertEquals(String.format("Product(id=null, name=%s, listProduct=null)", name), product.toString());
    }

    @Test
    void shouldReturnProductHashCode() {
        String name = "Product 1";
        Product product1 = new Product(name);
        Product product2 = new Product(name);
        assertNotNull(product1.hashCode());
        assertEquals(product1.hashCode(), product2.hashCode());
    }
}
