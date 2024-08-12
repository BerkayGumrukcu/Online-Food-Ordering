package com.restaurant.request;

import lombok.Data;

import java.util.List;

/**
 * Sepete yeni bir öğe eklemek için kullanılan istek modeli.
 *
 * Bu sınıf, sepet işlemi için gerekli olan yiyecek bilgilerini, miktarını ve ek bileşenlerini içerir.
 */
@Data
public class AddCartItemRequest {

    /**
     * Sepete eklenecek yiyeceğin benzersiz kimliği.
     * Bu ID, yiyeceğin veritabanında tanımlanmış olanı ile eşleşmelidir.
     */
    private Long foodId;

    /**
     * Sepete eklenecek yiyecekten kaç adet alınacağı.
     * Bu alan, yiyeceğin sepet üzerindeki miktarını belirtir.
     */
    private int quantity;

    /**
     * Yiyeceğe eklenmek istenen bileşenler.
     * Bu liste, kullanıcı tarafından seçilen ekstra malzemeleri veya özel istekleri temsil eder.
     */
    private List<String> ingredients;

}
