package com.restaurant.request;

import lombok.Data;

/**
 * Sepet öğesinin güncellenmesi için kullanılan istek modeli.
 *
 * Bu sınıf, sepet öğesinin kimliğini ve yeni miktarını içerir.
 */
@Data
public class UpdateCartItemRequest {

    /**
     * Güncellenmek istenen sepet öğesinin kimliği.
     * Bu alan, hangi sepet öğesinin güncelleneceğini belirtir.
     */
    private Long cartItemId;

    /**
     * Güncellenmiş miktar.
     * Bu alan, sepet öğesinin yeni miktarını içerir.
     */
    private int quantity;

}
