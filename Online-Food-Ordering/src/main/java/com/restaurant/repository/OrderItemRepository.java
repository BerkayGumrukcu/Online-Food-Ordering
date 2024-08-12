package com.restaurant.repository;

import com.restaurant.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OrderItem modeline ait veri erişim işlemlerini yöneten repository arayüzü.
 *
 * Spring Data JPA tarafından sağlanan JpaRepository arayüzünü genişleterek,
 * OrderItem modelinin CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
