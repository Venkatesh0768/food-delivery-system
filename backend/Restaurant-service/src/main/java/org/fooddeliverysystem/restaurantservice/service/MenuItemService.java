package org.fooddeliverysystem.restaurantservice.service;

import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.restaurantservice.dtos.MenuItemRequest;
import org.fooddeliverysystem.restaurantservice.model.MenuItem;
import org.fooddeliverysystem.restaurantservice.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItem createMenuItem(MenuItemRequest request){
        MenuItem menuItem = new MenuItem();
        menuItem.setMenuId(request.getMenuId());
        menuItem.setRestaurantId(request.getRestaurantId());
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setVeg(request.getVeg());
        menuItem.setImageUrl(request.getImageUrl());
        return menuItemRepository.save(menuItem);
    }

    public MenuItem getMenuItemById(java.util.UUID id){
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu Item not found"));
    }
}
