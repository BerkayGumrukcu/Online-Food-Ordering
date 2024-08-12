package com.restaurant.service;

import com.restaurant.model.Category;
import com.restaurant.model.Food;
import com.restaurant.model.Restaurant;
import com.restaurant.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    // Yeni bir yiyecek oluşturmak için kullanılır.
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    // Belirtilen ID'ye sahip bir yiyeceği silmek için kullanılır.
    public void deleteFood(Long foodId) throws Exception;

    // Belirli bir restorana ait yiyecekleri, verilen filtrelerle almak için kullanılır.
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonveg, boolean isSeasonal, String foodCategory);

    // Yiyecekleri anahtar kelimeye göre aramak için kullanılır.
    public List<Food> searchFood(String keyword);

    // Belirtilen ID'ye sahip bir yiyeceği bulmak için kullanılır.
    public Food findFoodById(Long foodId) throws Exception;

    // Belirtilen ID'ye sahip bir yiyeceğin mevcut olup olmadığını güncellemek için kullanılır.
    public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
