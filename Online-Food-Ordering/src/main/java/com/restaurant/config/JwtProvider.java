package com.restaurant.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtProvider {

    // JWT'ler için kullanılan gizli anahtar
    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Authentication nesnesinden JWT oluşturur
    public String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        // JWT'yi oluşturur ve geri döndürür
        String jwt = Jwts.builder()
                .setIssuedAt(new Date()) // Token'ın oluşturulma tarihi
                .setExpiration(new Date(new Date().getTime() + 86400000)) // Token'ın son geçerlilik tarihi (1 gün)
                .claim("email", auth.getName()) // Token'a e-posta bilgisi ekler
                .claim("authorities", roles) // Token'a yetkiler bilgisi ekler
                .signWith(key) // Token'ı gizli anahtar ile imzalar
                .compact(); // Token'ı oluşturur

        return jwt;
    }

    // JWT'den e-posta bilgisini çıkarır
    public String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7); // "Bearer " ifadesini çıkarır
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // JWT'yi imzalayan anahtarı belirler
                .build()
                .parseClaimsJws(jwt) // JWT'yi parse eder
                .getBody(); // JWT'nin gövdesini alır

        // E-posta bilgisini alır
        String email = String.valueOf(claims.get("email"));

        return email;
    }

    // Yetkileri virgülle ayrılmış bir dize olarak döndürür
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority()); // Yetkileri ekler
        }

        return String.join(",", auths); // Yetkileri virgülle ayırarak döndürür
    }
}
