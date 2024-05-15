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

import com.example.demo.list_product.dto.ListProductCreateRequestDto;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

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
    UserRepository userRepository;

    @Autowired
    ListProductRepository listProductRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        userRepository.deleteAll();
        listProductRepository.deleteAll();
    }

    /*
     * GET
     */

    /*
     * POST
     */

    @Test
    void shouldCreateListProduct() {
        Product product = productRepository.save(new Product("Product 1"));
        User user = userRepository.save(new User("John Doe", "john.doe@example.com"));
        Integer amount = 2;

        ResponseEntity<ListProductCreateResponseDto> response = restTemplate.postForEntity("/list-products",
                new ListProductCreateRequestDto(product.getId(), user.getId(), amount),
                ListProductCreateResponseDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        ListProductCreateResponseDto listProduct = response.getBody();
        assertNotNull(listProduct);
        assertEquals(product.getId(), listProduct.getId());
        assertEquals(user.getId(), listProduct.getUserId());
        assertEquals(product.getName(), listProduct.getName());
        assertEquals(amount, listProduct.getAmount());
    }

    @Test
    void shouldValidateBodyToCreateListProduct() {
        ListProductCreateRequestDto requestDto = new ListProductCreateRequestDto(1L, 1L, -1);
        ResponseEntity<ListProductCreateResponseDto> response = restTemplate.postForEntity("/list-products", requestDto,
                ListProductCreateResponseDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        requestDto.setAmount(0);
        requestDto.setProductId(null);
        ResponseEntity<ListProductCreateResponseDto> response2 = restTemplate.postForEntity("/list-products",
                requestDto, ListProductCreateResponseDto.class);
        assertEquals(response2.getStatusCode(), HttpStatus.BAD_REQUEST);

        requestDto.setProductId(1L);
        requestDto.setUserId(null);
        ResponseEntity<ListProductCreateResponseDto> response3 = restTemplate.postForEntity("/list-products",
                requestDto, ListProductCreateResponseDto.class);
        assertEquals(response3.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldNotAllowToCreateListProductFromInexistentUser() {
        Product product = productRepository.save(new Product("Product 1"));
        Long inexistantUserId = 1L;
        Integer amount = 2;

        ResponseEntity<ListProductCreateResponseDto> response = restTemplate.postForEntity("/list-products",
                new ListProductCreateRequestDto(product.getId(), inexistantUserId, amount),
                ListProductCreateResponseDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldNotAllowToCreateListProductFromInexistentProduct() {
        Long inexistantProductId = 1L;
        User user = userRepository.save(new User("John Doe", "john.doe@example.com"));
        Integer amount = 2;

        ResponseEntity<ListProductCreateResponseDto> response = restTemplate.postForEntity("/list-products",
                new ListProductCreateRequestDto(inexistantProductId, user.getId(), amount),
                ListProductCreateResponseDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    /*
     * PUT
     */

    /*
     * DELETE
     */
}
