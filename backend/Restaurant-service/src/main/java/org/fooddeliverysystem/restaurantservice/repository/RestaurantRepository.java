package org.fooddeliverysystem.restaurantservice.repository;

import org.fooddeliverysystem.restaurantservice.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    List<Restaurant> findByCityIgnoreCase(String city);
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    List<Restaurant> findByPureVeg(Boolean pureVeg);
}
