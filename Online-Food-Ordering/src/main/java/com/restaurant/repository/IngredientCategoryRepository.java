package com.restaurant.repository;

import com.restaurant.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * IngredientCategory modeline ait veri erişim işlemlerini yöneten repository arayüzü.
 *
 * Spring Data JPA tarafından sağlanan JpaRepository arayüzünü genişleterek,
 * IngredientCategory modelinin CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {

    /**
     * Belirli bir restoranın tüm malzeme kategorilerini bulur.
     *
     * @param id Restoranın ID'si.
     * @return Restorana ait malzeme kategorileri listesi.
     */
    List<IngredientCategory> findByRestaurantId(Long id);

}
