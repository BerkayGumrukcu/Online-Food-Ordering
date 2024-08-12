package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.RestaurantDto;
import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.request.CreateRestaurantRequest;

// Restoran yönetimi için servis arayüzü tanımlanıyor.
public interface RestaurantService {

    // Yeni bir restoran oluşturmak için kullanılan metod.
    // CreateRestaurantRequest ve User parametrelerini alır ve Restaurant nesnesi döndürür.
    Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    // Mevcut bir restoranı güncellemek için kullanılan metod.
    // Restoranın ID'si ve güncellenmiş bilgileri alır, güncellenmiş Restaurant nesnesi döndürür.
    Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    // Belirli bir ID'ye sahip restoranı silmek için kullanılan metod.
    // Restoranın ID'sini alır ve hiçbir şey döndürmez (void).
    void deleteRestaurant(Long restaurantId) throws Exception;

    // Tüm restoranların listesini döndürmek için kullanılan metod.
    List<Restaurant> getAllRestaurant();

    // Belirli bir anahtar kelime ile restoranları aramak için kullanılan metod.
    // Anahtar kelimeyi alır ve bu kelimeye uyan restoranların listesini döndürür.
    List<Restaurant> searchRestaurant(String keyword);

    // Belirli bir ID'ye sahip restoranı bulmak için kullanılan metod.
    // Restoranın ID'sini alır ve Restaurant nesnesi döndürür.
    Restaurant findRestaurantById(Long id) throws Exception;

    // Belirli bir kullanıcıya ait restoranı bulmak için kullanılan metod.
    // Kullanıcının ID'sini alır ve Restaurant nesnesi döndürür.
    Restaurant getRestaurantByUserId(Long userId) throws Exception;

    // Belirli bir restoranı kullanıcının favorilerine eklemek veya çıkarmak için kullanılan metod.
    // Restoranın ID'sini ve kullanıcıyı alır, favorilere eklenmiş veya çıkarılmış RestaurantDto nesnesini döndürür.
    RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    // Belirli bir restoranın açık veya kapalı durumunu güncellemek için kullanılan metod.
    // Restoranın ID'sini alır ve güncellenmiş Restaurant nesnesi döndürür.
    Restaurant updateRestaurantStatus(Long id) throws Exception;

}
