package com.ags.spring_ecommerce_bff.repository;

import com.ags.spring_ecommerce_bff.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {}
