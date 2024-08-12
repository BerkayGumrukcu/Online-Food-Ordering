package com.restaurant.service;

import com.restaurant.model.Cart;
import com.restaurant.model.CartItem;
import com.restaurant.model.Food;
import com.restaurant.model.User;
import com.restaurant.repository.CartItemRepository;
import com.restaurant.repository.CartRepository;
import com.restaurant.repository.FoodRepository;
import com.restaurant.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        // JWT token kullanarak kullanıcıyı bul
        User user = userService.findUserByJwtToken(jwt);

        // Yiyeceği ID ile bul
        Food food = foodService.findFoodById(req.getFoodId());

        // Kullanıcının sepetini bul
        Cart cart = cartRepository.findByCustomerId(user.getId());

        // Sepette zaten bu yiyecek var mı kontrol et
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                // Yiyecek varsa, miktarı güncelle
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        // Yiyecek sepet içinde değilse, yeni bir sepet öğesi oluştur
        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());

        // Yeni sepet öğesini veritabanına kaydet
        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        // Yeni öğeyi sepetin içine ekle
        cart.getItems().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        // Sepet öğesini ID ile bul
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()) {
            throw new Exception("Sepet öğesi bulunamadı.");
        }
        CartItem item = cartItemOptional.get();

        // Miktarı güncelle ve toplam fiyatı hesapla
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);

        // Güncellenmiş öğeyi veritabanına kaydet
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        // JWT token kullanarak kullanıcıyı bul
        User user = userService.findUserByJwtToken(jwt);
        // Kullanıcının sepetini bul
        Cart cart = cartRepository.findByCustomerId(user.getId());

        // Sepet öğesini ID ile bul
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()) {
            throw new Exception("Sepet öğesi bulunamadı.");
        }

        CartItem item = cartItemOptional.get();

        // Öğeyi sepetten çıkar
        cart.getItems().remove(item);

        // Güncellenmiş sepeti veritabanına kaydet
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;

        // Sepetteki her öğenin toplam fiyatını hesapla
        for (CartItem cartItem : cart.getItems()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        // Sepeti ID ile bul
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isEmpty()) {
            throw new Exception("Sepet bulunamadı: " + id);
        }

        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        // Kullanıcı ID ile sepeti bul
        Cart cart = cartRepository.findByCustomerId(userId);
        // Sepet toplamını hesapla ve sepet nesnesine ekle
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        // Kullanıcı ID ile sepeti bul
        Cart cart = findCartByUserId(userId);
        // Sepet içindeki tüm öğeleri temizle
        cart.getItems().clear();
        // Temizlenmiş sepeti veritabanına kaydet
        return cartRepository.save(cart);
    }
}
