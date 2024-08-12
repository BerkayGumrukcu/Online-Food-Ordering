package com.restaurant.repository;

import com.restaurant.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CartItem modeline ait veri erişim işlemlerini yöneten repository arayüzü.
 *
 * Spring Data JPA tarafından sağlanan JpaRepository arayüzünü genişleterek,
 * CartItem modelinin CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // JpaRepository, CartItem modelinin CRUD işlemlerini otomatik olarak sağlar.
    // Burada ek bir yöntem tanımlanmadığı için varsayılan JpaRepository yöntemleri kullanılabilir.

}
