package com.eilco.ecommerce.repository;

import com.eilco.ecommerce.model.entities.Category;
import com.eilco.ecommerce.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
