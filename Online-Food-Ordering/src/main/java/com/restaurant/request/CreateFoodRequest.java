package com.restaurant.request;

import com.restaurant.model.Category;
import com.restaurant.model.IngredientsItem;
import lombok.Data;

import java.util.List;

/**
 * Yeni bir yiyecek oluşturmak için kullanılan istek modeli.
 *
 * Bu sınıf, yiyeceğin adı, açıklaması, fiyatı, kategori bilgisi, fotoğrafları, restoran ID'si,
 * ve diğer özelliklerini içeren verileri temsil eder.
 */
@Data
public class CreateFoodRequest {

    /**
     * Yiyeceğin adı.
     * Bu, yiyeceğin tanımlayıcı adı olup kullanıcılar tarafından görülecektir.
     */
    private String name;

    /**
     * Yiyeceğin kısa açıklaması.
     * Bu açıklama, yiyeceğin içeriğini veya özelliklerini tanımlayan bilgileri içerir.
     */
    private String description;

    /**
     * Yiyeceğin fiyatı.
     * Bu alan, yiyeceğin satış fiyatını belirtir ve genellikle bir uzun (Long) değer olarak saklanır.
     */
    private Long price;

    /**
     * Yiyeceğin ait olduğu kategori.
     * Bu kategori, yiyeceğin sınıflandırılmasını sağlar ve `Category` nesnesi olarak temsil edilir.
     */
    private Category category;

    /**
     * Yiyeceğe ait fotoğrafların listesi.
     * Bu liste, yiyeceğin görsellerini içerir ve kullanıcıların yiyeceği görsel olarak değerlendirmesine olanak sağlar.
     */
    private List<String> images;

    /**
     * Yiyeceğin ait olduğu restoranın benzersiz kimliği.
     * Bu ID, yiyeceğin hangi restorana ait olduğunu belirtir.
     */
    private Long restaurantId;

    /**
     * Yiyeceğin vejetaryen olup olmadığı.
     * Eğer `true` ise, yiyecek vejetaryendir; `false` ise, et içerir.
     */
    private boolean vegetarian;

    /**
     * Yiyeceğin mevsimsel olup olmadığı.
     * Eğer `true` ise, yiyecek sadece belirli bir mevsimde bulunur; `false` ise, yıl boyunca mevcuttur.
     */
    private boolean sessional;

    /**
     * Yiyeceğin içerdiği malzemeler.
     * Bu liste, yiyeceğin içindeki malzemeleri temsil eder ve kullanıcıya yiyeceğin içeriği hakkında bilgi verir.
     */
    private List<IngredientsItem> ingredients;

}
