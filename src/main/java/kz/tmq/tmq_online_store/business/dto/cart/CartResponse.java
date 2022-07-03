package kz.tmq.tmq_online_store.business.dto.cart;

import lombok.Data;

@Data
public class CartResponse {

    private final boolean success;
    private final String message;

    public CartResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
