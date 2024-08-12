package com.restaurant.service;

import com.restaurant.dto.RestaurantDto;
import com.restaurant.model.Address;
import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.repository.AddressRepository;
import com.restaurant.repository.RestaurantRepository;
import com.restaurant.repository.UserRepository;
import com.restaurant.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class RestaurantServiceImp implements RestaurantService {

    // RestaurantRepository, AddressRepository ve UserRepository sınıflarını dependency injection ile bu sınıfa ekliyoruz.
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    // Yeni bir restoran oluşturmak için kullanılır.
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        // Gelen adres bilgilerini veritabanına kaydediyoruz.
        Address address = addressRepository.save(req.getAddress());

        // Yeni bir Restaurant nesnesi oluşturuyoruz ve gelen bilgileri bu nesneye set ediyoruz.
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now()); // Şu anki tarih ve saati kaydediyoruz.
        restaurant.setOwner(user); // Restoranın sahibi olarak kullanıcıyı set ediyoruz.

        // Restoranı veritabanına kaydedip geri döndürüyoruz.
        return restaurantRepository.save(restaurant);
    }

    // Mevcut bir restoranı güncellemek için kullanılır.
    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        // Verilen restaurantId ile restoranı buluyoruz.
        Restaurant restaurant = findRestaurantById(restaurantId);

        // Eğer mutfak türü boş değilse güncelliyoruz.
        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        // Eğer açıklama boş değilse güncelliyoruz.
        if (restaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        // Eğer isim boş değilse güncelliyoruz.
        if (restaurant.getName() != null) {
            restaurant.setName(updatedRestaurant.getName());
        }

        // Güncellenmiş restoranı veritabanına kaydedip geri döndürüyoruz.
        return restaurantRepository.save(restaurant);
    }

    // Restoranı silmek için kullanılır.
    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        // Verilen restaurantId ile restoranı buluyoruz.
        Restaurant restaurant = findRestaurantById(restaurantId);

        // Restoranı veritabanından siliyoruz.
        restaurantRepository.delete(restaurant);
    }

    // Tüm restoranları listelemek için kullanılır.
    @Override
    public List<Restaurant> getAllRestaurant() {

        // Tüm restoranları bulup geri döndürüyoruz.
        return restaurantRepository.findAll();
    }

    // Anahtar kelime ile restoranları aramak için kullanılır.
    @Override
    public List<Restaurant> searchRestaurant(String keyword) {

        // Anahtar kelimeye göre restoranları bulup geri döndürüyoruz.
        return restaurantRepository.findBySearchQuery(keyword);
    }

    // Belirli bir ID'ye sahip restoranı bulmak için kullanılır.
    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {

        // Verilen ID ile restoranı arıyoruz.
        Optional<Restaurant> opt = restaurantRepository.findById(id);

        // Eğer restoran bulunamazsa bir istisna fırlatıyoruz.
        if (opt.isEmpty()) {
            throw new Exception("Restorant Bulunamadı. " + id);
        }

        // Bulunan restoranı geri döndürüyoruz.
        return opt.get();
    }

    // Kullanıcı ID'sine göre restoran bulmak için kullanılır.
    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {

        // Verilen kullanıcı ID'si ile restoranı buluyoruz.
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        // Eğer restoran bulunamazsa bir istisna fırlatıyoruz.
        if (restaurant == null) {
            throw new Exception("Kullanıcıya ait restorant bulunamadı. " + userId);
        }

        // Bulunan restoranı geri döndürüyoruz.
        return restaurant;
    }

    // Bir restoranı favorilere eklemek veya çıkarmak için kullanılır.
    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {

        // Verilen restaurantId ile restoranı buluyoruz.
        Restaurant restaurant = findRestaurantById(restaurantId);

        // Restoranın bazı bilgilerini RestaurantDto nesnesine set ediyoruz.
        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        // Kullanıcının favori restoranlarını alıyoruz.
        boolean isFavorited = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for (RestaurantDto favorite : favorites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorited = true;
                break;
            }
        }

        // Eğer restoran favorilerde ise çıkarıyoruz, değilse ekliyoruz.
        if (isFavorited) {
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else {
            favorites.add(dto);
        }

        // Kullanıcıyı kaydedip güncel favorileri geri döndürüyoruz.
        userRepository.save(user);

        return dto;
    }

    // Restoranın açık veya kapalı durumunu güncellemek için kullanılır.
    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {

        // Verilen ID ile restoranı buluyoruz.
        Restaurant restaurant = findRestaurantById(id);

        // Restoranın açık veya kapalı durumunu tersine çeviriyoruz.
        restaurant.setOpen(!restaurant.isOpen());

        // Güncellenmiş restoranı kaydedip geri döndürüyoruz.
        return restaurantRepository.save(restaurant);
    }
}
