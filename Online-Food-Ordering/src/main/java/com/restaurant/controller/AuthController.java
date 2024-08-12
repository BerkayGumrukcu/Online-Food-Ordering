package com.restaurant.controller;

import com.restaurant.config.JwtProvider;
import com.restaurant.model.Cart;
import com.restaurant.model.USER_ROLE;
import com.restaurant.model.User;
import com.restaurant.repository.CartRepository;
import com.restaurant.repository.UserRepository;
import com.restaurant.request.LoginRequest;
import com.restaurant.response.AuthResponse;
import com.restaurant.service.CustomerUserDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Kullanıcı kimlik doğrulama ve yetkilendirme işlemlerini gerçekleştiren denetleyici.
 *
 * Bu denetleyici, kullanıcı kayıt ve giriş işlemlerini yönetir, JWT tabanlı kimlik doğrulama ve yetkilendirme sağlar.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    /**
     * Yeni bir kullanıcı kaydı oluşturur.
     *
     * @param user Kullanıcı kaydı için gerekli bilgileri içeren kullanıcı nesnesi.
     * @return Kullanıcı kaydının başarılı olduğunu belirten JWT ve rol bilgisi içeren yanıt.
     * @throws Exception Eğer kullanıcı e-posta adresi zaten kullanılıyorsa bir istisna fırlatır.
     */
    @PostMapping("/signup")
    @SneakyThrows
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        // E-posta adresinin zaten kullanılıp kullanılmadığını kontrol eder
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("Bu mail başka bir hesap tarafından kullanılmakta.");
        }

        // Yeni kullanıcıyı oluşturur
        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        // Kullanıcıyı veritabanına kaydeder
        User savedUser = userRepository.save(createdUser);

        // Kullanıcı için bir sepet oluşturur
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        // Kullanıcıyı doğrular ve JWT oluşturur
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        // Yanıt nesnesini oluşturur
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Kayıt Başarılı");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    /**
     * Mevcut bir kullanıcı için oturum açar.
     *
     * @param req Giriş yapmak için gerekli e-posta ve şifre bilgilerini içeren istek nesnesi.
     * @return Başarılı giriş yanıtı, JWT ve kullanıcı rolü bilgisi içerir.
     */
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) {
        String username = req.getEmail();
        String password = req.getPassword();

        // Kullanıcıyı doğrular
        Authentication authentication = authenticate(username, password);

        // JWT oluşturur
        String jwt = jwtProvider.generateToken(authentication);

        // Kullanıcının yetkilerini alır
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        // Yanıt nesnesini oluşturur
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Giriş Başarılı");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    /**
     * Kullanıcı adını ve şifreyi doğrular.
     *
     * @param username Kullanıcı adı.
     * @param password Kullanıcı şifresi.
     * @return Doğrulama için gerekli Authentication nesnesi.
     * @throws BadCredentialsException Eğer kullanıcı adı veya şifre geçersizse bir istisna fırlatır.
     */
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Geçersiz Kullanıcı Adı.");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Geçersiz Şifre.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
