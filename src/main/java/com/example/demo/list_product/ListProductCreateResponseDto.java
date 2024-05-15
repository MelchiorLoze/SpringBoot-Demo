package com.example.demo.list_product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListProductCreateResponseDto {

    private Long id;
    private Long userId;
    private String name;
    private Integer amount;
}
