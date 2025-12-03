package org.fooddeliverysystem.restaurantservice.controller;

import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.restaurantservice.dtos.MenuItemRequest;
import org.fooddeliverysystem.restaurantservice.dtos.MenuRequest;
import org.fooddeliverysystem.restaurantservice.service.MenuItemService;
import org.fooddeliverysystem.restaurantservice.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<?> createMenu(@RequestBody MenuRequest req) {
        return ResponseEntity.ok(menuService.createMenu(req));
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getMenus(@PathVariable UUID id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    @PostMapping("/items")
    public ResponseEntity<?> createMenuItem(@RequestBody MenuItemRequest req) {
        return ResponseEntity.ok(menuItemService.createMenuItem(req));
    }

    @GetMapping("/items/{menuId}")
    public ResponseEntity<?> getMenuItems(@PathVariable UUID menuId) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(menuId));
    }
}
