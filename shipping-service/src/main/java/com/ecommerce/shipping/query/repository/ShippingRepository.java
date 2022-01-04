package com.ecommerce.shipping.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.shipping.query.entity.ShippingEntity;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingEntity, String> {

}
