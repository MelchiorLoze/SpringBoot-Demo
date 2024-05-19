package com.example.demo.list_product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListProductResponseDto {

    private Long id;
    private Long userId;
    private String name;
    private Integer amount;
}
