package org.fooddeliverysystem.orderservice.client;

import org.fooddeliverysystem.orderservice.dto.MenuItemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "RESTAURANT-SERVICE")
public interface MenuClient {

    @GetMapping("menus/items/{menuId}")
    MenuItemResponse getMenuItem(@PathVariable("menuId") UUID id);
}
