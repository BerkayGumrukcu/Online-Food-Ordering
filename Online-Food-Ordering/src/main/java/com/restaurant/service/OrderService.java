package com.restaurant.service;

import com.restaurant.model.Order;
import com.restaurant.model.User;
import com.restaurant.request.OrderRequest;

import java.util.List;

// Sipariş yönetimi için servis arayüzü tanımlanıyor.
public interface OrderService {

    // Yeni bir sipariş oluşturmak için kullanılan metod.
    // OrderRequest ve User parametrelerini alır, oluşturulan Order nesnesini döndürür.
    public Order createOrder(OrderRequest order, User user) throws Exception;

    // Mevcut bir siparişin durumunu güncellemek için kullanılan metod.
    // Siparişin ID'sini ve yeni durumunu alır, güncellenmiş Order nesnesini döndürür.
    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    // Belirli bir ID'ye sahip siparişi iptal etmek için kullanılan metod.
    // Siparişin ID'sini alır ve hiçbir şey döndürmez (void).
    public void cancelOrder(Long orderId) throws Exception;

    // Belirli bir ID'ye sahip siparişi bulmak için kullanılan metod.
    // Siparişin ID'sini alır ve Order nesnesini döndürür.
    public Order findOrderById(Long orderId) throws Exception;

    // Belirli bir kullanıcıya ait tüm siparişleri listeler.
    // Kullanıcının ID'sini alır, bu kullanıcıya ait Order nesnelerinin listesini döndürür.
    public List<Order> getUsersOrder(Long userId) throws Exception;

    // Belirli bir restorana ait siparişleri listeler, isteğe bağlı olarak belirli bir sipariş durumuna göre filtreleme yapar.
    // Restoranın ID'sini ve isteğe bağlı olarak sipariş durumunu alır, bu restoranla ilgili Order nesnelerinin listesini döndürür.
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception;

}
