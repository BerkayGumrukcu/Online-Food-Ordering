package com.restaurant.controller;

import com.restaurant.model.Category;
import com.restaurant.model.User;
import com.restaurant.service.CategoryService;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * Yeni bir kategori oluşturur.
     *
     * @param category Oluşturulacak kategori bilgilerini içeren istek nesnesi.
     * @param jwt Kullanıcı kimliğini doğrulamak için kullanılan JWT.
     * @return Oluşturulan kategori bilgilerini içeren yanıt.
     * @throws Exception Eğer kategori oluşturma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestHeader ("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Category createdCategory = categoryService.createCategory(category.getName(), user.getId());

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    /**
     * Kullanıcının restoranına ait kategorileri alır.
     *
     * @param jwt Kullanıcı kimliğini doğrulamak için kullanılan JWT.
     * @return Kullanıcının restoranına ait kategorileri içeren yanıt.
     * @throws Exception Eğer kategori alma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader ("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Category> categories = categoryService.findCategoryByRestaurantId(user.getId());

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
