package com.restaurant.repository;

import com.restaurant.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Cart modeline ait veri erişim işlemlerini yöneten repository arayüzü.
 *
 * Spring Data JPA tarafından sağlanan JpaRepository arayüzünü genişleterek,
 * Cart modelinin CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Belirli bir kullanıcıya ait sepeti bulur.
     *
     * @param userId Kullanıcının ID'si.
     * @return Kullanıcıya ait sepet.
     */
    public Cart findByCustomerId(Long userId);

}
