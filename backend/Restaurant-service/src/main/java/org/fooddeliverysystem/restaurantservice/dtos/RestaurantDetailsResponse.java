package org.fooddeliverysystem.restaurantservice.dtos;

import lombok.*;
import org.fooddeliverysystem.restaurantservice.model.Menu;
import org.fooddeliverysystem.restaurantservice.model.MenuItem;
import org.fooddeliverysystem.restaurantservice.model.Restaurant;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class RestaurantDetailsResponse {
    private Restaurant restaurant;
    private List<Menu>  Menus;
    private List<MenuItem> menuItems;
}
