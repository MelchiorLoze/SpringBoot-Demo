package com.example.demo.list_product;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.demo.list_product.dto.ListProductCreateRequestDto;
import com.example.demo.product.Product;
import com.example.demo.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.product.ProductRepository;
import com.example.demo.product.exeption.ProductNotFoundException;
import com.example.demo.user.UserRepository;
import com.example.demo.user.exception.UserForbiddenException;

class ListProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ListProductRepository listProductRepository;

    @InjectMocks
    private ListProductService listProductService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateListProductWithExistingProduct() {
        // Arrange
        ListProductCreateRequestDto dto = new ListProductCreateRequestDto(1L, 1L, 1);

        User user = new User();
        user.setId(1L);

        Product product = new Product("Product 1");
        product.setId(1L);

        ListProduct listProduct = new ListProduct(product, user, 1);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));
        when(listProductRepository.save(any(ListProduct.class))).thenReturn(listProduct);

        // Act
        ListProduct result = listProductService.createListProduct(dto);

        // Assert
        assertEquals(listProduct, result);
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(productRepository, times(1)).findById(any(Long.class));
        verify(listProductRepository, times(1)).save(any(ListProduct.class));
        verifyNoMoreInteractions(userRepository, productRepository, listProductRepository);
    }

    @Test
    void shouldCreateListProductWithNewProduct() {
        // Arrange
        ListProductCreateRequestDto dto = new ListProductCreateRequestDto("Product 1", 1L, 1);

        User user = new User();
        user.setId(1L);

        Product product = new Product("Product 1");
        product.setId(1L);

        ListProduct listProduct = new ListProduct(product, user, 1);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(listProductRepository.save(any(ListProduct.class))).thenReturn(listProduct);

        // Act
        ListProduct result = listProductService.createListProduct(dto);

        // Assert
        assertEquals(listProduct, result);
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(productRepository, times(1)).save(any(Product.class));
        verify(listProductRepository, times(1)).save(any(ListProduct.class));
        verifyNoMoreInteractions(userRepository, productRepository, listProductRepository);
    }

    @Test
    void shouldThrowUserForbiddenException() {
        // Arrange
        ListProductCreateRequestDto dto = new ListProductCreateRequestDto(1L, 1L, 1);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserForbiddenException.class, () -> listProductService.createListProduct(dto));
        verify(userRepository, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(productRepository, listProductRepository);
    }

    @Test
    void shouldThrowProductNotFoundException() {
        // Arrange
        ListProductCreateRequestDto dto = new ListProductCreateRequestDto(1L, 1L, 1);

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> listProductService.createListProduct(dto));
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(productRepository, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(userRepository, productRepository);
        verifyNoInteractions(listProductRepository);
    }
}