package com.restaurant.repository;

import com.restaurant.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Address modeline ait veri erişim işlemlerini yöneten repository arayüzü.
 *
 * Spring Data JPA tarafından sağlanan JpaRepository arayüzünü genişleterek,
 * Address modelinin CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    // JpaRepository, Address modelinin CRUD işlemlerini otomatik olarak sağlar.
    // Burada ek bir yöntem tanımlanmadığı için varsayılan JpaRepository yöntemleri kullanılabilir.

}
