package com.restaurant.dto;

import lombok.Data;

import java.util.List;

/**
 * Restoran bilgilerini taşıyan veri transfer nesnesi.
 *
 * Bu sınıf, restoranla ilgili bilgileri taşır ve
 * genellikle uygulama katmanları arasında veri iletimi için kullanılır.
 */
@Data
public class RestaurantDto {

    private String title;

    private List<String> images;

    private String description;

    private Long id;

}
