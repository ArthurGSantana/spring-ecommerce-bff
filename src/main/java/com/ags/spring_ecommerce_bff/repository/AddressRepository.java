package com.ags.spring_ecommerce_bff.repository;

import com.ags.spring_ecommerce_bff.entity.Address;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
  List<Address> findByUserId(UUID userId);
}
