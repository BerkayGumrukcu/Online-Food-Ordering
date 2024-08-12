package com.restaurant.service;

import com.restaurant.model.IngredientCategory;
import com.restaurant.model.IngredientsItem;

import java.util.List;

// Malzeme yönetimi için servis arayüzü tanımlanıyor.
public interface IngredientsService {

    // Yeni bir malzeme kategorisi oluşturmak için kullanılan metod.
    // Kategori adını ve restoran ID'sini alır, oluşturulan IngredientCategory nesnesini döndürür.
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    // Belirli bir ID'ye sahip malzeme kategorisini bulmak için kullanılan metod.
    // Kategori ID'sini alır, IngredientCategory nesnesini döndürür.
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    // Belirli bir restorana ait tüm malzeme kategorilerini bulmak için kullanılan metod.
    // Restoran ID'sini alır, bu restorana ait IngredientCategory nesnelerinin listesini döndürür.
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    // Belirli bir restorana ait tüm malzemeleri bulmak için kullanılan metod.
    // Restoran ID'sini alır, bu restorana ait IngredientsItem nesnelerinin listesini döndürür.
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);

    // Yeni bir malzeme öğesi oluşturmak için kullanılan metod.
    // Restoran ID'sini, malzeme adını ve kategori ID'sini alır, oluşturulan IngredientsItem nesnesini döndürür.
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    // Belirli bir malzemenin stok durumunu güncellemek için kullanılan metod.
    // Malzeme ID'sini alır, güncellenmiş IngredientsItem nesnesini döndürür.
    public IngredientsItem updateStock(Long id) throws Exception;
}
