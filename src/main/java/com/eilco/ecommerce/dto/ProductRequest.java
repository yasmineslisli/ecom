package com.eilco.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private boolean active = true;
    private String imageUrl;
    private Long categoryId;
}
