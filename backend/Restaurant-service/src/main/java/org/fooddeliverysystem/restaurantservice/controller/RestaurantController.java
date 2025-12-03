package org.fooddeliverysystem.restaurantservice.controller;

import lombok.RequiredArgsConstructor;
import org.fooddeliverysystem.restaurantservice.dtos.RestaurantRequest;
import org.fooddeliverysystem.restaurantservice.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RestaurantRequest req) {
        return ResponseEntity.ok(restaurantService.createRestaurant(req));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(restaurantService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        return ResponseEntity.ok(restaurantService.getById(id));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<?> getByCity(@PathVariable String city) {
        return ResponseEntity.ok(restaurantService.getByCity(city));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String q) {
        return ResponseEntity.ok(restaurantService.getByName(q));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam(required = false) Boolean veg) {
        if(veg!=null && veg) {
            return ResponseEntity.ok(restaurantService.getByPureVeg(true));
        }
        return ResponseEntity.ok(restaurantService.getAll());
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<?> getFullDetails(@PathVariable UUID id) {
        return ResponseEntity.ok(restaurantService.getRestaurantDetails(id));
    }
}
