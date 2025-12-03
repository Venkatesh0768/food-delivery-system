package org.fooddeliverysystem.restaurantservice.repository;

import org.fooddeliverysystem.restaurantservice.model.MenuItem;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,UUID> {
    List<MenuItem> findByMenuId(UUID menuId);
    List<MenuItem> findByRestaurantId(UUID restaurantId);
}
