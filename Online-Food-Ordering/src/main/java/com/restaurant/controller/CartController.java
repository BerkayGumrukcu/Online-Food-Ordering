package com.restaurant.controller;

import com.restaurant.model.Cart;
import com.restaurant.model.CartItem;
import com.restaurant.model.User;
import com.restaurant.request.AddCartItemRequest;
import com.restaurant.request.UpdateCartItemRequest;
import com.restaurant.service.CartService;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    /**
     * Sepete yeni bir ürün ekler.
     *
     * @param req Sepete eklenecek ürünün bilgilerini içeren istek nesnesi.
     * @param jwt Kullanıcı kimliğini doğrulamak için kullanılan JWT.
     * @return Sepete eklenen ürün bilgilerini içeren yanıt.
     * @throws Exception Eğer ürün ekleme işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(req, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    /**
     * Sepetteki ürünün miktarını günceller.
     *
     * @param req Güncellenecek ürünün ID'si ve yeni miktarını içeren istek nesnesi.
     * @param jwt Kullanıcı kimliğini doğrulamak için kullanılan JWT.
     * @return Güncellenmiş ürün bilgilerini içeren yanıt.
     * @throws Exception Eğer ürün güncelleme işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,
                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    /**
     * Sepetten bir ürünü kaldırır.
     *
     * @param id Kaldırılacak ürünün ID'si.
     * @param jwt Kullanıcı kimliğini doğrulamak için kullanılan JWT.
     * @return Güncellenmiş sepet bilgilerini içeren yanıt.
     * @throws Exception Eğer ürün kaldırma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /**
     * Kullanıcının sepetini temizler.
     *
     * @param jwt Kullanıcı kimliğini doğrulamak için kullanılan JWT.
     * @return Temizlenmiş sepet bilgilerini içeren yanıt.
     * @throws Exception Eğer sepet temizleme işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /**
     * Kullanıcının sepetini bulur.
     *
     * @param jwt Kullanıcı kimliğini doğrulamak için kullanılan JWT.
     * @return Kullanıcının sepet bilgilerini içeren yanıt.
     * @throws Exception Eğer sepet bulma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
