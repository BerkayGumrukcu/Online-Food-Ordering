package com.restaurant.response;

import lombok.Data;

/**
 * API yanıtlarında kullanılacak basit mesaj modeli.
 * Bu sınıf, genellikle başarı veya hata mesajları gibi kısa metinlerin yanıt olarak döndürülmesi için kullanılır.
 */
@Data
public class MessageResponse {

    /**
     * Yanıt mesajı.
     */
    private String message;

}
