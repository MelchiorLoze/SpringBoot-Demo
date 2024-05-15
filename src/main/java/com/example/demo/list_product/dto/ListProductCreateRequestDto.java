package com.example.demo.list_product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListProductCreateRequestDto {

    @NotNull
    private Long productId;

    @NotNull
    private Long userId;

    @PositiveOrZero
    private Integer amount = 1;
}
