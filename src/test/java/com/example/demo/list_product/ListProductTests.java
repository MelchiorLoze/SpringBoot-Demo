package com.example.demo.list_product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import com.example.demo.product.Product;

public class ListProductTests {

    @Test
    void shouldReturnListProductAsString() {
        String name = "My Product";
        Product product = new Product(name);
        Integer amount = 1;
        ListProduct listProduct = new ListProduct(product, amount);
        assertEquals(
                String.format("ListProduct(id=null, product=Product(id=null, name=%s, listProduct=null), amount=%d)",
                        name, amount),
                listProduct.toString());
    }

    @Test
    void shouldReturnListProductHashCode() {
        String name = "My Product";
        Product product = new Product(name);
        Integer amount = 1;
        ListProduct listProduct1 = new ListProduct(product, amount);
        ListProduct listProduct2 = new ListProduct(product, amount);
        assertNotNull(listProduct1.hashCode());
        assertEquals(listProduct1.hashCode(), listProduct2.hashCode());
    }
}
