package com.restaurant.repository;

import com.restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User modeline ait veri erişim işlemlerini yöneten repository arayüzü.
 *
 * Spring Data JPA tarafından sağlanan JpaRepository arayüzünü genişleterek,
 * User modelinin CRUD (Create, Read, Update, Delete) işlemlerini sağlar.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Verilen e-posta adresine sahip kullanıcıyı getirir.
     *
     * Bu metod, kullanıcı adı olarak e-posta adresini kullanarak veritabanında
     * kullanıcı araması yapar. Bu, kullanıcı doğrulama işlemlerinde kullanılabilir.
     *
     * @param username E-posta adresi (kullanıcı adı olarak kullanılır)
     * @return E-posta adresine sahip kullanıcı
     */
    User findByEmail(String username);

}
