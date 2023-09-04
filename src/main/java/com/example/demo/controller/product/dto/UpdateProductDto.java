package com.example.demo.controller.product.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateProductDto {

    private String title;

    private Long price;

    private UUID categoryId;

}
