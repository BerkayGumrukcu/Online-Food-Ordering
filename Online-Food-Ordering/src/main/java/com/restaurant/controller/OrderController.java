package com.restaurant.controller;

import com.restaurant.model.Order;
import com.restaurant.model.User;
import com.restaurant.request.OrderRequest;
import com.restaurant.service.OrderService;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * Yeni bir sipariş oluşturur.
     *
     * @param req Sipariş oluşturmak için gerekli bilgileri içeren istek.
     * @param jwt Kullanıcı kimlik doğrulama jetonu.
     * @return Oluşturulan siparişi döner.
     * @throws Exception Eğer sipariş oluşturma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req,
                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req, user);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Kullanıcının sipariş geçmişini getirir.
     *
     * @param jwt Kullanıcı kimlik doğrulama jetonu.
     * @return Kullanıcının sipariş geçmişini içeren listeyi döner.
     * @throws Exception Eğer sipariş geçmişini alma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getUsersOrder(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
