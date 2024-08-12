package com.restaurant.request;

import com.restaurant.model.Address;
import com.restaurant.model.ContactInformation;
import lombok.Data;

import java.util.List;

/**
 * Yeni bir restoran oluşturmak için kullanılan istek modeli.
 *
 * Bu sınıf, restoranın adı, açıklaması, mutfak türü, adresi, iletişim bilgileri,
 * açılış saatleri ve fotoğraflarını içeren verileri temsil eder.
 */
@Data
public class CreateRestaurantRequest {

    /**
     * Restoranın benzersiz kimliği.
     * Bu alan, restoranın veritabanındaki kimliğini temsil eder ve genellikle yeni restoranlar
     * oluşturulurken otomatik olarak atanır.
     */
    private Long id;

    /**
     * Restoranın adı.
     * Bu, restoranın tanımlayıcı adıdır ve kullanıcılar tarafından görülecektir.
     */
    private String name;

    /**
     * Restoranın kısa açıklaması.
     * Bu açıklama, restoranın özelliklerini, sunduğu yemekleri veya diğer bilgileri içerir.
     */
    private String description;

    /**
     * Restoranın mutfak türü.
     * Bu alan, restoranın sunduğu yemek türünü belirtir (örneğin, İtalyan, Türk, Çin mutfağı vb.).
     */
    private String cuisineType;

    /**
     * Restoranın adresi.
     * Bu, restoranın fiziksel konumunu belirten `Address` nesnesidir.
     */
    private Address address;

    /**
     * Restoranın iletişim bilgileri.
     * Bu, restoranın telefon numarası, e-posta adresi vb. gibi iletişim bilgilerini içeren `ContactInformation` nesnesidir.
     */
    private ContactInformation contactInformation;

    /**
     * Restoranın açılış saatleri.
     * Bu, restoranın hafta içi ve hafta sonu açılış ve kapanış saatlerini belirten metindir.
     */
    private String openingHours;

    /**
     * Restoranın fotoğraflarının listesi.
     * Bu liste, restoranın görsel temsilini sağlayan fotoğrafları içerir ve genellikle URL'ler veya dosya yolları şeklindedir.
     */
    private List<String> images;

}
