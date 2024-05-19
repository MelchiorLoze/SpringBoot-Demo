package com.example.demo.list_product;

import org.springframework.stereotype.Service;

import com.example.demo.list_product.dto.ListProductCreateRequestDto;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.exeption.ProductNotFoundException;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.exception.UserForbiddenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListProductService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ListProductRepository listProductRepository;

    public ListProduct createListProduct(ListProductCreateRequestDto listProductCreateRequestDto) {
        User user = userRepository.findById(listProductCreateRequestDto.getUserId())
                .orElseThrow(UserForbiddenException::new);

        Product product;
        if (listProductCreateRequestDto.isCreateProduct()) {
            product = productRepository.save(new Product(listProductCreateRequestDto.getProductName()));
        } else {
            product = productRepository.findById(listProductCreateRequestDto.getProductId())
                    .orElseThrow(ProductNotFoundException::new);
        }

        ListProduct listProduct = new ListProduct(product, user, listProductCreateRequestDto.getAmount());
        listProductRepository.save(listProduct);

        return listProduct;
    }

}
