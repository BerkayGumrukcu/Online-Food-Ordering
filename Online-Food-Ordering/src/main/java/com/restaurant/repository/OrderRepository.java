package com.restaurant.repository;

import com.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Order modeline ait veri erişim işlemlerini yöneten repository arayüzü.
 *
 * Spring Data JPA tarafından sağlanan JpaRepository arayüzünü genişleterek,
 * Order modelinin CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Verilen müşteri ID'sine sahip tüm siparişleri getirir.
     *
     * @param userId Müşteri ID'si
     * @return Müşteriye ait siparişlerin listesi
     */
    public List<Order> findByCustomerId(Long userId);

    /**
     * Verilen restoran ID'sine sahip tüm siparişleri getirir.
     *
     * @param restaurantId Restoran ID'si
     * @return Restorana ait siparişlerin listesi
     */
    public List<Order> findByRestaurantId(Long restaurantId);

}
