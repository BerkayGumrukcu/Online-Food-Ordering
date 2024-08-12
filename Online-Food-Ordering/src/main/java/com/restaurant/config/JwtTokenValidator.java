package com.restaurant.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // İstekten JWT'yi alır
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null) {
            // "Bearer " ifadesini çıkarır
            jwt = jwt.substring(7);

            try {
                // Gizli anahtar ile JWT'yi doğrular
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key) // İmzalama anahtarını belirler
                        .build()
                        .parseClaimsJws(jwt) // JWT'yi parse eder
                        .getBody(); // JWT'nin gövdesini alır

                // JWT'den e-posta ve yetkileri alır
                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                // Yetkileri ayrıştırır
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                // Kullanıcı kimlik doğrulama nesnesini oluşturur
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth);

                // Authentication nesnesini SecurityContext'e ayarlar
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Token geçersizse hata fırlatır
                throw new BadCredentialsException("Geçersiz Token.......");
            }
        }

        // İsteği bir sonraki filtreye iletir
        filterChain.doFilter(request, response);
    }
}
