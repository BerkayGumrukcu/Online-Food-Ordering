package com.restaurant.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AppConfig {

    // Bean olarak SecurityFilterChain nesnesini tanımlar
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Oturum yönetimini stateless (durumsuz) olarak ayarlar
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
                        // /api/admin/** yoluna erişim sadece 'RESTAURANT_OWNER' veya 'ADMIN' rolüne sahip kullanıcılar için izinlidir
                        .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER", "ADMIN")
                        // /api/** yoluna erişim sadece kimlik doğrulama gerektirir
                        .requestMatchers("/api/**").authenticated()
                        // Diğer tüm yollar için erişim izni verilir
                        .anyRequest().permitAll()
                )
                // JwtTokenValidator adlı özel filtreyi, BasicAuthenticationFilter öncesinde çalışacak şekilde ekler
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                // CSRF korumasını devre dışı bırakır
                .csrf(csrf -> csrf.disable())
                // CORS yapılandırmasını belirtir ve corsConfigrationSource yöntemini kullanır
                .cors(cors -> cors.configurationSource(corsConfigrationSource()));

        return http.build();
    }

    // CORS yapılandırmasını oluşturan yöntem
    private CorsConfigurationSource corsConfigrationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                // İzin verilen kökenleri (origins) belirtir
                cfg.setAllowedOrigins(Arrays.asList(
                        "https://zosh-food.vercel.app",
                        "http://localhost:3000"
                ));
                // İzin verilen HTTP yöntemlerini belirtir
                cfg.setAllowedMethods(Collections.singletonList("*"));
                // Çerezlerin (cookies) paylaşımına izin verir
                cfg.setAllowCredentials(true);
                // İzin verilen başlıkları belirtir
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                // İstemci tarafına döndürülen başlıkları belirtir
                cfg.setExposedHeaders(List.of("Authorization"));
                // CORS isteğinin önbellek süresini belirtir
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    // Şifrelerin güvenli bir şekilde şifrelenmesini sağlayan PasswordEncoder nesnesini tanımlar
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
