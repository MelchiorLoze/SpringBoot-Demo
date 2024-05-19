package com.example.demo.list_product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.list_product.dto.ListProductCreateRequestDto;
import com.example.demo.list_product.dto.ListProductCreateResponseDto;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.exeption.ProductNotFoundException;
import com.example.demo.user.User;
import com.example.demo.user.exception.UserForbiddenException;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/list-products")
public class ListProductController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ListProductRepository listProductRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListProductCreateResponseDto create(
            @Valid @RequestBody ListProductCreateRequestDto listProductCreateRequestDto) {
        User user = entityManager.find(User.class, listProductCreateRequestDto.getUserId());
        if (user == null)
            throw new UserForbiddenException();

        Product product;
        if (listProductCreateRequestDto.isCreateProduct()) {
            product = productRepository.save(new Product(listProductCreateRequestDto.getProductName()));
        } else {
            product = entityManager.find(Product.class, listProductCreateRequestDto.getProductId());
            if (product == null)
                throw new ProductNotFoundException();
        }

        ListProduct listProduct = new ListProduct(product, user, listProductCreateRequestDto.getAmount());
        listProductRepository.save(listProduct);

        ListProductCreateResponseDto listProductCreateResponseDto = new ListProductCreateResponseDto(
                listProduct.getId(), listProduct.getUser().getId(), listProduct.getProduct().getName(),
                listProduct.getAmount());

        return listProductCreateResponseDto;
    }
}
