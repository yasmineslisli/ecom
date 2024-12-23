package com.eilco.ecommerce.dto;

import com.eilco.ecommerce.model.entities.Category;
import com.eilco.ecommerce.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryResponse {
    Category category;
}
