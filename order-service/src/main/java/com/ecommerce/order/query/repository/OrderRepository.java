package com.ecommerce.order.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.order.query.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

}
