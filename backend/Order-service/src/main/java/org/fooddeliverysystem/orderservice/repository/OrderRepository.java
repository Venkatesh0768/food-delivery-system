package org.fooddeliverysystem.orderservice.repository;

import org.fooddeliverysystem.orderservice.model.Order;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order , UUID> {
}
