package com.restaurant.controller;

import com.restaurant.model.Food;
import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.request.CreateFoodRequest;
import com.restaurant.response.MessageResponse;
import com.restaurant.service.FoodService;
import com.restaurant.service.RestaurantService;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Yöneticiye yönelik yiyecek yönetimi API'si.
 *
 * Bu denetleyici, yiyeceklerin oluşturulması, silinmesi ve güncellenmesi gibi işlemleri
 * yönetir. Yalnızca yetkili yöneticiler bu işlemleri gerçekleştirebilir.
 */
@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Yeni bir yiyecek oluşturur.
     *
     * @param req Yiyecek oluşturma talebi içeren veri.
     * @param jwt Yöneticinin kimlik doğrulama belirteci.
     * @return Oluşturulan yiyeceğin yanıtı.
     * @throws Exception Eğer bir hata oluşursa.
     */
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req, @RequestHeader ("Authorization") String jwt) throws Exception {
        // JWT'yi kullanarak kullanıcıyı doğrular
        User user = userService.findUserByJwtToken(jwt);

        // Restoranı ID ile bulur
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());

        // Yiyeceği oluşturur ve kaydeder
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        // Oluşturulan yiyeceği yanıt olarak döner
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    /**
     * Belirtilen yiyeceği siler.
     *
     * @param id Silinecek yiyeceğin ID'si.
     * @param jwt Yöneticinin kimlik doğrulama belirteci.
     * @return Silme işlemi hakkında bilgi içeren yanıt.
     * @throws Exception Eğer yiyecek bulunamazsa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id, @RequestHeader ("Authorization") String jwt) throws Exception {
        // JWT'yi kullanarak kullanıcıyı doğrular
        User user = userService.findUserByJwtToken(jwt);

        // Yiyeceği siler
        foodService.deleteFood(id);

        // İşlem başarılı yanıtını oluşturur
        MessageResponse res = new MessageResponse();
        res.setMessage("Yiyecek silindi.");

        // Yanıtı döner
        return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
    }

    /**
     * Belirtilen yiyeceğin kullanılabilirlik durumunu günceller.
     *
     * @param id Güncellenecek yiyeceğin ID'si.
     * @param jwt Yöneticinin kimlik doğrulama belirteci.
     * @return Güncellenmiş yiyeceğin yanıtı.
     * @throws Exception Eğer yiyecek bulunamazsa.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id, @RequestHeader ("Authorization") String jwt) throws Exception {
        // JWT'yi kullanarak kullanıcıyı doğrular
        User user = userService.findUserByJwtToken(jwt);

        // Yiyeceğin kullanılabilirlik durumunu günceller
        Food food = foodService.updateAvailabilityStatus(id);

        // Güncellenmiş yiyeceği yanıt olarak döner
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
