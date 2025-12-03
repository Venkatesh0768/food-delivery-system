package org.fooddeliverysystem.restaurantservice.service;


import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.restaurantservice.dtos.RestaurantDetailsResponse;
import org.fooddeliverysystem.restaurantservice.dtos.RestaurantRequest;
import org.fooddeliverysystem.restaurantservice.model.Menu;
import org.fooddeliverysystem.restaurantservice.model.MenuItem;
import org.fooddeliverysystem.restaurantservice.model.Restaurant;
import org.fooddeliverysystem.restaurantservice.repository.MenuItemRepository;
import org.fooddeliverysystem.restaurantservice.repository.MenuRepository;
import org.fooddeliverysystem.restaurantservice.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public Restaurant createRestaurant(RestaurantRequest req) {

        Restaurant r = new Restaurant();

        r.setOwnerId(req.getOwnerId());
        r.setName(req.getName());
        r.setDescription(req.getDescription());

        r.setAddressLine1(req.getAddressLine1());
        r.setAddressLine2(req.getAddressLine2());
        r.setCity(req.getCity());
        r.setState(req.getState());
        r.setPincode(req.getPincode());

        r.setLatitude(req.getLatitude());
        r.setLongitude(req.getLongitude());

        return restaurantRepository.save(r);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant getById(UUID id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    public List<Restaurant> getByCity(String city) {
        return restaurantRepository.findByCityIgnoreCase(city);
    }

    public List<Restaurant> getByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Restaurant> getByPureVeg(Boolean pureVeg) {
        return restaurantRepository.findByPureVeg(pureVeg);
    }


    public RestaurantDetailsResponse getRestaurantDetails(UUID id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Restaurant not found")
        );

        List<Menu> menus = menuRepository.findByRestaurantId(id);
        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(id);

        return new RestaurantDetailsResponse(restaurant, menus, menuItems);
    }

}
