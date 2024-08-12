package com.restaurant.controller;

import com.restaurant.dto.RestaurantDto;
import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.request.CreateRestaurantRequest;
import com.restaurant.service.RestaurantService;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    /**
     * Restoranları anahtar kelimeye göre arar.
     *
     * @param jwt Kimlik doğrulama jetonu.
     * @param keyword Arama anahtar kelimesi.
     * @return Arama sonuçlarını içeren restoran listesi.
     * @throws Exception Eğer arama işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    /**
     * Tüm restoranları getirir.
     *
     * @param jwt Kimlik doğrulama jetonu.
     * @return Tüm restoranları içeren liste.
     * @throws Exception Eğer restoranları alma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    /**
     * Belirli bir restoranı ID ile bulur.
     *
     * @param jwt Kimlik doğrulama jetonu.
     * @param id Restoran ID'si.
     * @return Belirli bir restoranı içeren yanıt.
     * @throws Exception Eğer restoranı bulma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    /**
     * Belirli bir restoranı favorilere ekler.
     *
     * @param jwt Kimlik doğrulama jetonu.
     * @param id Restoran ID'si.
     * @return Favorilere eklenen restoranı içeren yanıt.
     * @throws Exception Eğer favorilere ekleme işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        RestaurantDto dto = restaurantService.addToFavorites(id, user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
