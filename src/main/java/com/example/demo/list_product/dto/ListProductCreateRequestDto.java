package com.example.demo.list_product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ValidListProductCreateRequestDto
public class ListProductCreateRequestDto {

    @NotNull
    private boolean createProduct;

    private Long productId;
    private String productName;

    @NotNull
    private Long userId;

    @PositiveOrZero
    private Integer amount = 1;

    public ListProductCreateRequestDto(Long productId, Long userId, Integer amount) {
        this.createProduct = false;
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
    }

    public ListProductCreateRequestDto(String productName, Long userId, Integer amount) {
        this.createProduct = true;
        this.productName = productName;
        this.userId = userId;
        this.amount = amount;
    }
}
