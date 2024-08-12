package com.restaurant.service;

import com.restaurant.model.*;
import com.restaurant.repository.AddressRepository;
import com.restaurant.repository.OrderItemRepository;
import com.restaurant.repository.OrderRepository;
import com.restaurant.repository.UserRepository;
import com.restaurant.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    // Repository ve diğer servislerin dependency injection ile bu sınıfa eklenmesi.
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    // Yeni bir sipariş oluşturmak için kullanılan metod.
    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        // Teslimat adresini kaydediyoruz.
        Address shippAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippAddress);

        // Eğer kullanıcı bu adresi daha önce kaydetmemişse, kullanıcı adreslerine ekliyoruz.
        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        // Sipariş verilen restoranı buluyoruz.
        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());

        // Yeni bir Order nesnesi oluşturup gerekli bilgileri set ediyoruz.
        Order createOrder = new Order();
        createOrder.setCustomer(user);
        createOrder.setCreatedAt(new Date()); // Sipariş oluşturulma tarihini şu anki tarih olarak ayarlıyoruz.
        createOrder.setOrderStatus("Gönderiliyor"); // Sipariş durumu başlangıçta "Gönderiliyor" olarak ayarlanıyor.
        createOrder.setDeliveryAddress(savedAddress);
        createOrder.setRestaurant(restaurant);

        // Kullanıcının sepetini buluyoruz.
        Cart cart = cartService.findCartByUserId(user.getId());

        // Sipariş öğelerini oluşturup kaydediyoruz.
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        // Toplam sipariş tutarını hesaplıyoruz.
        Long totalPrice = cartService.calculateCartTotals(cart);

        // Siparişin öğelerini ve toplam fiyatını ayarlıyoruz.
        createOrder.setItems(orderItems);
        createOrder.setTotalPrice(totalPrice);

        // Siparişi kaydediyoruz ve restoranın siparişlerine ekliyoruz.
        Order savedOrder = orderRepository.save(createOrder);
        restaurant.getOrders().add(savedOrder);

        return createOrder; // Oluşturulan siparişi geri döndürüyoruz.
    }

    // Sipariş durumunu güncellemek için kullanılan metod.
    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);

        // Geçerli sipariş durumlarını kontrol ediyoruz ve durumu güncelliyoruz.
        if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Lütfen geçerli bir sipariş durumu seçiniz.");
    }

    // Siparişi iptal etmek için kullanılan metod.
    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);

        // Siparişi veritabanından siliyoruz.
        orderRepository.deleteById(orderId);
    }

    // Belirli bir ID'ye sahip siparişi bulmak için kullanılan metod.
    @Override
    public Order findOrderById(Long orderId) throws Exception {

        // Siparişi veritabanından ID ile arıyoruz.
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new Exception("Sipariş Bulunamadı.");
        }

        return optionalOrder.get(); // Bulunan siparişi geri döndürüyoruz.
    }

    // Belirli bir kullanıcıya ait tüm siparişleri bulmak için kullanılan metod.
    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    // Belirli bir restorana ait tüm siparişleri ve belirli bir duruma göre filtreleme yapmak için kullanılan metod.
    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {

        // Restorana ait tüm siparişleri alıyoruz.
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        // Eğer bir sipariş durumu belirtilmişse, siparişleri bu duruma göre filtreliyoruz.
        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }

        return orders; // Filtrelenmiş sipariş listesini geri döndürüyoruz.
    }
}
