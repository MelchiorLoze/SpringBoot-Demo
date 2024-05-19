package com.example.demo.list_product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.list_product.dto.ListProductCreateRequestDto;
import com.example.demo.list_product.dto.ListProductResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/list-products")
@RequiredArgsConstructor
public class ListProductController {

    private final ListProductService listProductService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListProductResponseDto create(@Valid @RequestBody ListProductCreateRequestDto listProductCreateRequestDto) {
        ListProduct listProduct = listProductService.createListProduct(listProductCreateRequestDto);

        ListProductResponseDto listProductCreateResponseDto = new ListProductResponseDto(listProduct.getId(),
                listProduct.getUser().getId(), listProduct.getProduct().getName(), listProduct.getAmount());

        return listProductCreateResponseDto;
    }
}
