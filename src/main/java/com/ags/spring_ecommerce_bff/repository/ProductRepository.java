package com.ags.spring_ecommerce_bff.repository;

import com.ags.spring_ecommerce_bff.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
