package com.ags.spring_ecommerce_bff.repository;

import com.ags.spring_ecommerce_bff.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
