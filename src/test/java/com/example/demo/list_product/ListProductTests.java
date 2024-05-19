package com.example.demo.list_product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import com.example.demo.product.Product;
import com.example.demo.user.User;

public class ListProductTests {

    @Test
    void shouldCompareListProducts() {
        Product product1 = new Product("Product 1");
        Product product2 = new Product("Product 2");
        User user = new User("John Doe", "john.doe@example.com");
        Integer amount = 1;
        ListProduct listProduct1 = new ListProduct(product1, user, amount);
        ListProduct listProduct2 = new ListProduct(product1, user, amount);
        ListProduct listProduct3 = new ListProduct(product2, user, amount);
        assertEquals(listProduct1, listProduct1);
        assertEquals(listProduct1, listProduct2);
        assertNotEquals(listProduct1, listProduct3);
    }

    @Test
    void shouldReturnListProductAsString() {
        Product product = new Product("My Product");
        User user = new User("John Doe", "john.doe@example.com");
        Integer amount = 1;
        ListProduct listProduct = new ListProduct(product, user, amount);
        assertEquals(String.format(
                "ListProduct(id=null, product=Product(id=null, name=%s, listProduct=null), user=User(id=null, name=%s, email=%s, listProducts=null), amount=%d)",
                product.getName(), user.getName(), user.getEmail(), amount), listProduct.toString());
    }

    @Test
    void shouldReturnListProductHashCode() {
        Product product = new Product("My Product");
        User user = new User("John Doe", "john.doe@example.com");
        Integer amount = 1;
        ListProduct listProduct1 = new ListProduct(product, user, amount);
        ListProduct listProduct2 = new ListProduct(product, user, amount);
        assertNotNull(listProduct1.hashCode());
        assertEquals(listProduct1.hashCode(), listProduct2.hashCode());
    }
}
