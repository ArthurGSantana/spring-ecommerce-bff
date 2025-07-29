package com.ags.spring_ecommerce_bff.repository;

import com.ags.spring_ecommerce_bff.entity.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {}
