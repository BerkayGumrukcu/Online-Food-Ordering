package com.restaurant.controller;

import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.request.CreateRestaurantRequest;
import com.restaurant.response.MessageResponse;
import com.restaurant.service.RestaurantService;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Yöneticiye yönelik restoran yönetim API'si.
 *
 * Bu denetleyici, restoran oluşturma, güncelleme, silme ve durumunu güncelleme işlemlerini gerçekleştirir.
 * Ayrıca, bir yöneticinin bağlı olduğu restoranı bulmak için de kullanılabilir.
 */
@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    /**
     * Yeni bir restoran oluşturur.
     *
     * @param req Restoran oluşturma isteği için gerekli veriler.
     * @param jwt Yöneticinin kimlik doğrulama belirteci.
     * @return Oluşturulan restoranın yanıtı.
     * @throws Exception Eğer restoran oluşturulurken bir hata oluşursa.
     */
    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        // JWT'yi kullanarak yöneticiyi doğrular
        User user = userService.findUserByJwtToken(jwt);

        // Restoranı oluşturur
        Restaurant restaurant = restaurantService.createRestaurant(req, user);

        // Oluşturulan restoranı yanıt olarak döner
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    /**
     * Var olan bir restoranın bilgilerini günceller.
     *
     * @param req Restoran güncelleme isteği için gerekli veriler.
     * @param jwt Yöneticinin kimlik doğrulama belirteci.
     * @param id Güncellenmesi gereken restoranın ID'si.
     * @return Güncellenmiş restoranın yanıtı.
     * @throws Exception Eğer restoran güncellenirken bir hata oluşursa.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        // JWT'yi kullanarak yöneticiyi doğrular
        User user = userService.findUserByJwtToken(jwt);

        // Restoranın bilgilerini günceller
        Restaurant restaurant = restaurantService.updateRestaurant(id, req);

        // Güncellenmiş restoranı yanıt olarak döner
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    /**
     * Belirli bir restoranı siler.
     *
     * @param jwt Yöneticinin kimlik doğrulama belirteci.
     * @param id Silinmesi gereken restoranın ID'si.
     * @return Silme işleminin sonucunu belirten mesaj yanıtı.
     * @throws Exception Eğer restoran silinirken bir hata oluşursa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        // JWT'yi kullanarak yöneticiyi doğrular
        User user = userService.findUserByJwtToken(jwt);

        // Restoranı siler
        restaurantService.deleteRestaurant(id);

        // Silme işleminin sonucunu belirten mesajı yanıt olarak döner
        MessageResponse res = new MessageResponse();
        res.setMessage("Restoran silindi.");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * Belirli bir restoranın durumunu günceller.
     *
     * @param jwt Yöneticinin kimlik doğrulama belirteci.
     * @param id Güncellenmesi gereken restoranın ID'si.
     * @return Güncellenmiş restoranın yanıtı.
     * @throws Exception Eğer restoranın durumu güncellenirken bir hata oluşursa.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        // JWT'yi kullanarak yöneticiyi doğrular
        User user = userService.findUserByJwtToken(jwt);

        // Restoranın durumunu günceller
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);

        // Güncellenmiş restoranı yanıt olarak döner
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    /**
     * Yöneticinin bağlı olduğu restoranı getirir.
     *
     * @param jwt Yöneticinin kimlik doğrulama belirteci.
     * @return Yöneticinin bağlı olduğu restoranın yanıtı.
     * @throws Exception Eğer restoran bulunamazsa veya getirilirken bir hata oluşursa.
     */
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        // JWT'yi kullanarak yöneticiyi doğrular
        User user = userService.findUserByJwtToken(jwt);

        // Yöneticinin bağlı olduğu restoranı getirir
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        // Restoranı yanıt olarak döner
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
