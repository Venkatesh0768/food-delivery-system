package org.fooddeliverysystem.restaurantservice.service;


import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.restaurantservice.dtos.MenuRequest;
import org.fooddeliverysystem.restaurantservice.model.Menu;
import org.fooddeliverysystem.restaurantservice.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu createMenu(MenuRequest request) {
        Menu menu = new Menu();

        menu.setRestaurantId(request.getRestaurantId());
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());

        return menuRepository.save(menu);
    }

    public Menu getMenuById(UUID id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
    }
}
