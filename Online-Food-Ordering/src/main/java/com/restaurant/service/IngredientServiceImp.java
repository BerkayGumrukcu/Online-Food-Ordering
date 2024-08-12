package com.restaurant.service;

import com.restaurant.model.IngredientCategory;
import com.restaurant.model.IngredientsItem;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.IngredientCategoryRepository;
import com.restaurant.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientsService {

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    // Restoran servisinde kullanılan bağımlılık tanımlanıyor.
    private RestaurantService restaurantService;

    // Yeni bir malzeme kategorisi oluşturmak için kullanılan metod.
    // Restoran ID'si ile birlikte kategori adını alır ve oluşturulan kategori nesnesini döndürür.
    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        // Restoranı bulmak için restoran servisi kullanılıyor.
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = new IngredientCategory();
        category.setRestaurant(restaurant);
        category.setName(name);

        return ingredientCategoryRepository.save(category);
    }

    // Belirli bir ID'ye sahip malzeme kategorisini bulmak için kullanılan metod.
    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {

        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);

        if (opt.isEmpty()) {
            throw new Exception("Kategori Bulunamadı.");
        }

        return opt.get();
    }

    // Belirli bir restorana ait tüm malzeme kategorilerini bulmak için kullanılan metod.
    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {

        // Restoranın varlığını kontrol etmek için restoran servisi kullanılıyor.
        restaurantService.findRestaurantById(id);

        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    // Belirli bir restorana ait tüm malzemeleri bulmak için kullanılan metod.
    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {

        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    // Yeni bir malzeme öğesi oluşturmak için kullanılan metod.
    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredient = ingredientItemRepository.save(item);
        category.getIngredients().add(ingredient);

        return ingredient;
    }

    // Belirli bir malzemenin stok durumunu güncellemek için kullanılan metod.
    @Override
    public IngredientsItem updateStock(Long id) throws Exception {

        Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepository.findById(id);

        if (optionalIngredientsItem.isEmpty()) {
            throw new Exception("İçerik Bulunamadı.");
        }
        IngredientsItem ingredientsItem = optionalIngredientsItem.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());

        return ingredientItemRepository.save(ingredientsItem);
    }
}
