package com.restaurant.repository;

import com.restaurant.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Food modeline ait veri erişim işlemlerini yöneten repository arayüzü.
 *
 * Spring Data JPA tarafından sağlanan JpaRepository arayüzünü genişleterek,
 * Food modelinin CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
public interface FoodRepository extends JpaRepository<Food, Long> {

    /**
     * Belirli bir restoranın tüm yemeklerini bulur.
     *
     * @param restaurantId Restoranın ID'si.
     * @return Restorana ait yemek listesi.
     */
    List<Food> findByRestaurantId(Long restaurantId);

    /**
     * Yemeklerin adında veya kategori adında belirli bir anahtar kelimeyi arar.
     *
     * @param keyword Aranacak anahtar kelime.
     * @return Anahtar kelimeyi içeren yemekler listesi.
     */
    @Query("SELECT f FROM Food f WHERE f.name LIKE %:keyword% OR f.foodCategory.name LIKE %:keyword%")
    List<Food> searchFood(@Param("keyword") String keyword);

}
