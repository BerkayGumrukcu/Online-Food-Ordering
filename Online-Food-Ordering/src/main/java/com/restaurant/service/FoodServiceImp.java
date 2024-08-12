package com.restaurant.service;

import com.restaurant.model.Category;
import com.restaurant.model.Food;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.FoodRepository;
import com.restaurant.request.CreateFoodRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService {

    // Yiyeceklerle ilgili veritabanı işlemleri için repository.
    private FoodRepository foodRepository;

    // Yeni bir yiyecek oluşturmak için kullanılan metod.
    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {

        // Yeni bir Food nesnesi oluşturuluyor ve gerekli alanlar dolduruluyor.
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setSeassonal(req.isSessional());
        food.setVegetarian(req.isVegetarian());

        // Yiyecek kaydediliyor ve restoranın yiyecek listesine ekleniyor.
        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    // Belirli bir yiyeceği silmek için kullanılan metod.
    @Override
    public void deleteFood(Long foodId) throws Exception {
        // Yiyeceği ID ile buluyoruz.
        Food food = findFoodById(foodId);

        // Restoran ile ilişkisi kesiliyor ve yiyecek güncelleniyor.
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    // Belirli bir restorana ait yiyecekleri belirli filtrelerle getirmek için kullanılan metod.
    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonveg, boolean isSeasonal, String foodCategory) {

        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        // Filtreler uygulanıyor.
        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }
        if (isNonveg){
            foods = filterByNonVeg(foods, isNonveg);
        }
        if (isSeasonal){
            foods = filterBySeasonal(foods,isSeasonal);
        }
        if (foodCategory != null && !foodCategory.equals("")){
            foods = filterByCategory(foods, foodCategory);
        }

        return foods;
    }

    // Yiyecekleri kategoriye göre filtrelemek için kullanılan yardımcı metod.
    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    // Yiyecekleri mevsimsel durumuna göre filtrelemek için kullanılan yardımcı metod.
    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeassonal() == isSeasonal).collect(Collectors.toList());
    }

    // Yiyecekleri non-vejetaryen olup olmadığına göre filtrelemek için kullanılan yardımcı metod.
    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
    }

    // Yiyecekleri vejetaryen olup olmadığına göre filtrelemek için kullanılan yardımcı metod.
    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
    }

    // Anahtar kelime ile yiyecek araması yapmak için kullanılan metod.
    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    // ID ile yiyecek bulmak için kullanılan metod.
    @Override
    public Food findFoodById(Long foodId) throws Exception {

        Optional<Food> optionalFood = foodRepository.findById(foodId);

        if (optionalFood.isEmpty()){
            throw new Exception("Yiyecek Bulunamadı.");
        }

        return optionalFood.get();
    }

    // Yiyeceğin mevcut olup olmadığını güncellemek için kullanılan metod.
    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {

        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
