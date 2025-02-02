package com.eilco.ecommerce.dto;

import com.eilco.ecommerce.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private boolean active = true;
    private String imageurl;
    private Long categoryId;
    private String categoryName;
}
