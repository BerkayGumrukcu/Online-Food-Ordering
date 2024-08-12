package com.restaurant.service;

import com.restaurant.model.USER_ROLE;
import com.restaurant.model.User;
import com.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Spring Security için kullanıcı detaylarını yönetir.
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Kullanıcı bilgilerini veritabanından almak için kullanılan repository.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Veritabanından kullanıcıyı email ile bulur.
        User user = userRepository.findByEmail(username);
        if (user == null) {
            // Kullanıcı bulunamazsa bir istisna fırlatır.
            throw new UsernameNotFoundException("Kullanıcı Bulunamadı: " + username);
        }

        // Kullanıcının rolünü alır.
        USER_ROLE role = user.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Kullanıcının rolünü yetki listesine ekler.
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        // UserDetails nesnesi oluşturur ve döner. Bu nesne, kullanıcı adı, şifre ve yetkileri içerir.
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
