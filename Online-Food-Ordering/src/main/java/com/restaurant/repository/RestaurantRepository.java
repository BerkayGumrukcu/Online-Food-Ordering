package com.restaurant.repository;

import com.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Restaurant modeline ait veri erişim işlemlerini yöneten repository arayüzü.
 *
 * Spring Data JPA tarafından sağlanan JpaRepository arayüzünü genişleterek,
 * Restaurant modelinin CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    /**
     * Arama sorgusuna göre restoranları getirir.
     *
     * Restoran adı veya mutfak türü içinde verilen sorgu kelimesini içeren restoranları döndürür.
     * Sorgu kelimesi küçük harfe dönüştürülerek arama yapılır.
     *
     * @param query Arama sorgusu
     * @return Sorguya uyan restoranların listesi
     */
    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%', :query, '%')) OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")
    List<Restaurant> findBySearchQuery(String query);

    /**
     * Verilen kullanıcı ID'sine sahip restoranı getirir.
     *
     * @param userId Kullanıcı ID'si
     * @return Kullanıcıya ait restoran
     */
    Restaurant findByOwnerId(Long userId);

}
