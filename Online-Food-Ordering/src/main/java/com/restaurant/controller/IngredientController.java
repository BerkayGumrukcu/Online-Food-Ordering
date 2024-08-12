package com.restaurant.controller;

import com.restaurant.model.IngredientCategory;
import com.restaurant.model.IngredientsItem;
import com.restaurant.request.IngredientCategoryRequest;
import com.restaurant.request.IngredientRequest;
import com.restaurant.service.IngredientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    private IngredientsService ingredientsService;

    /**
     * Malzeme kategorisi oluşturur.
     *
     * @param req Malzeme kategorisi oluşturmak için gerekli bilgileri içeren istek.
     * @return Oluşturulan malzeme kategorisini döner.
     * @throws Exception Eğer malzeme kategorisi oluşturma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
    ) throws Exception {
        IngredientCategory item = ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    /**
     * Malzeme oluşturur.
     *
     * @param req Malzeme oluşturmak için gerekli bilgileri içeren istek.
     * @return Oluşturulan malzemeyi döner.
     * @throws Exception Eğer malzeme oluşturma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientRequest req
    ) throws Exception {
        IngredientsItem item = ingredientsService.createIngredientItem(req.getRestaurantId(), req.getName(), req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    /**
     * Belirli bir malzemenin stok bilgisini günceller.
     *
     * @param id Malzeme ID'si.
     * @return Güncellenmiş malzemeyi döner.
     * @throws Exception Eğer malzeme stok güncelleme işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {
        IngredientsItem item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * Belirli bir restoranın malzemelerini getirir.
     *
     * @param id Restoran ID'si.
     * @return Restoranın malzemelerini içeren listeyi döner.
     * @throws Exception Eğer malzeme alma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsItem> items = ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Belirli bir restoranın malzeme kategorilerini getirir.
     *
     * @param id Restoran ID'si.
     * @return Restoranın malzeme kategorilerini içeren listeyi döner.
     * @throws Exception Eğer malzeme kategorilerini alma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategories(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientCategory> items = ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
