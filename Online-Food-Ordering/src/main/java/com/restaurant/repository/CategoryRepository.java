package com.restaurant.repository;

import com.restaurant.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Category modeline ait veri erişim işlemlerini yöneten repository arayüzü.
 *
 * Spring Data JPA tarafından sağlanan JpaRepository arayüzünü genişleterek,
 * Category modelinin CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Belirli bir restorana ait kategorileri bulur.
     *
     * @param id Restoranın ID'si.
     * @return Restorana ait kategori listesi.
     */
    public List<Category> findByRestaurantId(Long id);

}
