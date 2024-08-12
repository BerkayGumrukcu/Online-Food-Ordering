package com.restaurant.service;

import com.restaurant.model.User;

// UserService adında bir interface tanımlıyoruz. Bu interface, UserServiceImp sınıfı gibi
// bu interface'i uygulayan sınıflar için şablon işlevi görür.
public interface UserService {

    // JWT token üzerinden kullanıcıyı bulmak için bir metod tanımlıyoruz.
    // Bu metod, JWT token'ı parametre olarak alır ve User nesnesi döndürür.
    User findUserByJwtToken(String jwt) throws Exception;

    // Email adresi üzerinden kullanıcıyı bulmak için bir metod tanımlıyoruz.
    // Bu metod, e-posta adresini parametre olarak alır ve User nesnesi döndürür.
    User findUserByEmail(String email) throws Exception;

}
