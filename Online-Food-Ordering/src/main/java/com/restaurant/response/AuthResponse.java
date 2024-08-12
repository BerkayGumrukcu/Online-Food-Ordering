package com.restaurant.response;

import com.restaurant.model.USER_ROLE;
import lombok.Data;

/**
 * Kimlik doğrulama işlemi sonrasında döndürülen yanıt modeli.
 *
 * Bu sınıf, kullanıcı giriş işlemi başarılı olduğunda sunucu tarafından döndürülen JWT, mesaj ve kullanıcı rolünü içeren bir yanıtı temsil eder.
 */
@Data
public class AuthResponse {

    /**
     * Kullanıcı kimlik doğrulama için verilen JSON Web Token (JWT).
     * Bu token, kullanıcının kimliğini doğrulamak ve API'ye erişim sağlamak için kullanılır.
     */
    private String jwt;

    /**
     * Yanıt mesajı.
     * Bu alan, işlemle ilgili bilgi veya doğrulama mesajları içerebilir.
     */
    private String message;

    /**
     * Kullanıcının rolü.
     * Bu alan, kullanıcının sahip olduğu rolü belirtir ve genellikle yetkilendirme işlemleri için kullanılır.
     */
    private USER_ROLE role;
}
