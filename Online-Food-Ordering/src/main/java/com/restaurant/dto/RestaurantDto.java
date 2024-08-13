package com.restaurant.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

/**
 * Restoran bilgilerini taşıyan veri transfer nesnesi.
 * Bu sınıf, restoranla ilgili bilgileri taşır ve
 * genellikle uygulama katmanları arasında veri iletimi için kullanılır.
 */
@Data
@Embeddable
public class RestaurantDto {

    private String title;

    @Column(length = 1000)
    private List<String> images;

    private String description;

    private Long id;

}
