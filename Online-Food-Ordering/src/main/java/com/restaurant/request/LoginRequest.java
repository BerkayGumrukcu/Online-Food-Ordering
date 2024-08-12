package com.restaurant.request;

import lombok.Data;

/**
 * Kullanıcı giriş işlemleri için kullanılan istek modeli.
 *
 * Bu sınıf, kullanıcı adı (e-posta) ve şifre bilgilerini içerir.
 */
@Data
public class LoginRequest {

    /**
     * Kullanıcının e-posta adresi.
     * Bu alan, kullanıcının kimliğini doğrulamak için kullanılır.
     */
    private String email;

    /**
     * Kullanıcının şifresi.
     * Bu alan, kullanıcının kimliğini doğrulamak için kullanılır ve güvenli bir şekilde saklanmalıdır.
     */
    private String password;

}
