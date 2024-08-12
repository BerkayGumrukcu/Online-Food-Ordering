package com.restaurant.controller;

import com.restaurant.model.Food;
import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.request.CreateFoodRequest;
import com.restaurant.service.FoodService;
import com.restaurant.service.RestaurantService;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Belirli bir isimle yemekleri arar.
     *
     * @param name Aranacak yemek adı.
     * @param jwt Kullanıcı kimliğini doğrulamak için kullanılan JWT.
     * @return Arama sonuçlarını içeren yemek listesini döner.
     * @throws Exception Eğer yemek arama işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.searchFood(name);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    /**
     * Belirli bir restoran için yemekleri getirir.
     *
     * @param vageterian Vejetaryen yemekleri dahil etmek için kullanılan parametre.
     * @param seasonal Mevsimsel yemekleri dahil etmek için kullanılan parametre.
     * @param nonveg Et yemeyen yemekleri dahil etmek için kullanılan parametre.
     * @param food_category Yemek kategorisi (isteğe bağlı).
     * @param restaurantId Restoran ID'si.
     * @param jwt Kullanıcı kimliğini doğrulamak için kullanılan JWT.
     * @return Belirtilen kriterlere göre yemekleri içeren listeyi döner.
     * @throws Exception Eğer yemekleri getirme işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vageterian,
                                                        @RequestParam boolean seasonal,
                                                        @RequestParam boolean nonveg,
                                                        @RequestParam(required = false) String food_category,
                                                        @PathVariable Long restaurantId,
                                                        @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vageterian, seasonal, nonveg, food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

}
