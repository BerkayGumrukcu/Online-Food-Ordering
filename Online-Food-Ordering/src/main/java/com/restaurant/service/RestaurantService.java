package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.RestaurantDto;
import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.request.CreateRestaurantRequest;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    void deleteRestaurant(Long restaurantId) throws Exception;

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

    Restaurant findRestaurantById(Long id) throws Exception;

    Restaurant getRestaurantByUserId(Long userId) throws Exception;

    RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    Restaurant updateRestaurantStatus(Long id) throws Exception;

}
