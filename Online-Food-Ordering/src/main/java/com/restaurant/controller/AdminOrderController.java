package com.restaurant.controller;

import com.restaurant.model.Order;
import com.restaurant.model.User;
import com.restaurant.service.OrderService;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Yöneticiye yönelik sipariş yönetimi API'si.
 * Bu denetleyici, restoran siparişlerini görüntüleme ve sipariş durumlarını güncelleme gibi işlemleri
 * yönetir. Yalnızca yetkili yöneticiler bu işlemleri gerçekleştirebilir.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * Restoranın sipariş geçmişini getirir.
     *
     * @param id Restoranın ID'si.
     * @param order_status (isteğe bağlı) Siparişin durumu (örneğin: "kabul edildi", "teslim edildi").
     * @param jwt Yöneticinin kimlik doğrulama belirteci.
     * @return Belirtilen restoranın sipariş listesi.
     * @throws Exception Eğer bir hata oluşursa.
     */
    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt) throws Exception {
        // JWT'yi kullanarak kullanıcıyı doğrular
        User user = userService.findUserByJwtToken(jwt);

        // Siparişleri getirir
        List<Order> orders = orderService.getRestaurantsOrder(id, order_status);

        // Sipariş listesini yanıt olarak döner
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Belirtilen siparişin durumunu günceller.
     *
     * @param id Güncellenecek siparişin ID'si.
     * @param orderStatus Yeni sipariş durumu.
     * @param jwt Yöneticinin kimlik doğrulama belirteci.
     * @return Güncellenmiş siparişin yanıtı.
     * @throws Exception Eğer sipariş bulunamazsa veya güncelleme sırasında bir hata oluşursa.
     */
    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception {
        // JWT'yi kullanarak kullanıcıyı doğrular
        User user = userService.findUserByJwtToken(jwt);

        // Siparişin durumunu günceller
        Order order = orderService.updateOrder(id, orderStatus);

        // Güncellenmiş siparişi yanıt olarak döner
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
