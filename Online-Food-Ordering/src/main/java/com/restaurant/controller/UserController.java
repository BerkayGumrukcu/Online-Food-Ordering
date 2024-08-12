package com.restaurant.controller;

import com.restaurant.model.User;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * JWT'ye göre kullanıcı profili bulur.
     *
     * @param jwt Kimlik doğrulama jetonu.
     * @return Kullanıcı profil bilgilerini içeren yanıt.
     * @throws Exception Eğer kullanıcı profili alma işlemi sırasında bir hata oluşursa bir istisna fırlatır.
     */
    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
