package com.restaurant.service;

import com.restaurant.config.JwtProvider;
import com.restaurant.model.User;
import com.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    // UserRepository ve JwtProvider sınıflarını dependency injection ile bu sınıfa ekliyoruz.
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    // JWT token üzerinden kullanıcıyı bulmak için bu metodu kullanıyoruz.
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        // JWT token içinden email bilgisini alıyoruz.
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        // Aldığımız email ile kullanıcıyı bulmak için başka bir metodu çağırıyoruz.
        User user = findUserByEmail(email);

        // Bulunan kullanıcıyı geri döndürüyoruz.
        return user;
    }

    // Email adresi üzerinden kullanıcıyı bulmak için bu metodu kullanıyoruz.
    @Override
    public User findUserByEmail(String email) throws Exception {
        // UserRepository üzerinden email adresine sahip kullanıcıyı buluyoruz.
        User user = userRepository.findByEmail(email);

        // Eğer kullanıcı bulunamazsa, bir exception fırlatıyoruz.
        if (user == null) {
            throw new Exception("Kullanıcı Bulunamadı."); // Türkçe bir hata mesajı veriyoruz.
        }

        // Kullanıcı bulunursa, bu kullanıcıyı geri döndürüyoruz.
        return user;
    }
}
