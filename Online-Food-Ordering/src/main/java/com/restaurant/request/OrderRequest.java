package com.restaurant.request;

import com.restaurant.model.Address;
import lombok.Data;

/**
 * Sipariş oluşturma işlemleri için kullanılan istek modeli.
 *
 * Bu sınıf, siparişin yapılacağı restoranın kimliğini ve teslimat adresini içerir.
 */
@Data
public class OrderRequest {

    /**
     * Siparişin verileceği restoranın kimliği.
     * Bu alan, siparişin hangi restorana ait olduğunu belirtir.
     */
    private Long restaurantId;

    /**
     * Teslimat adresi.
     * Bu alan, siparişin teslim edileceği adres bilgilerini içerir.
     */
    private Address deliveryAddress;

}
