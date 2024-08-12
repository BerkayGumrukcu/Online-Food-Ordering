package com.restaurant.service;

import com.restaurant.model.Category;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private RestaurantService restaurantService; // Restoran bilgilerini almak için kullanılan servis.

    @Autowired
    private CategoryRepository categoryRepository; // Kategori verilerini veritabanında yönetmek için kullanılan repository.

    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        // Kullanıcının restoranını alır.
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);

        // Yeni bir kategori oluşturur ve restoran ile ilişkilendirir.
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        // Kategoriyi veritabanına kaydeder ve döner.
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        // Restoranı alır ve belirtilen ID'ye sahip restorana ait kategorileri alır.
        Restaurant restaurant = restaurantService.getRestaurantByUserId(id);
        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        // Belirtilen ID'ye sahip kategoriyi bulur.
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            // Kategori bulunamazsa bir istisna fırlatır.
            throw new Exception("Kategori bulunamadı");
        }

        // Kategoriyi döner.
        return optionalCategory.get();
    }
}
