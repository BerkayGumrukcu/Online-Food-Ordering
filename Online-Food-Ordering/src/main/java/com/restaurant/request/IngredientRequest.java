package com.restaurant.request;

import lombok.Data;

/**
 * Yeni bir malzeme oluşturmak için kullanılan istek modeli.
 *
 * Bu sınıf, malzemenin adı, malzemenin ait olduğu kategori ve restoran hakkında bilgi içerir.
 */
@Data
public class IngredientRequest {

    /**
     * Malzemenin adı.
     * Bu alan, oluşturulacak malzemenin tanımlayıcı adını belirtir.
     */
    private String name;

    /**
     * Malzemenin ait olduğu kategori kimliği.
     * Bu, malzemenin hangi kategoriye ait olduğunu belirtir ve genellikle kategori yönetimiyle ilişkilidir.
     */
    private Long categoryId;

    /**
     * Malzemenin ilişkilendirileceği restoranın kimliği.
     * Bu, malzemenin hangi restorana ait olduğunu belirtir ve genellikle restoran malzeme envanterini yönetmek için kullanılır.
     */
    private Long restaurantId;

}
