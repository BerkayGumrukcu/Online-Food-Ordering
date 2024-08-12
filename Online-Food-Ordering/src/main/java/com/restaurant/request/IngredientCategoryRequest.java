package com.restaurant.request;

import lombok.Data;

/**
 * Yeni bir malzeme kategorisi oluşturmak için kullanılan istek modeli.
 *
 * Bu sınıf, malzeme kategorisinin adı ve ilişkilendirileceği restoranın kimliğini içerir.
 */
@Data
public class IngredientCategoryRequest {

    /**
     * Malzeme kategorisinin adı.
     * Bu alan, oluşturulacak malzeme kategorisinin adını belirler.
     */
    private String name;

    /**
     * Malzeme kategorisinin ilişkilendirileceği restoranın kimliği.
     * Bu, malzeme kategorisinin hangi restorana ait olduğunu belirtir.
     */
    private Long restaurantId;

}
