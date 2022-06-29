package kz.tmq.tmq_online_store.business.dto.cart;

import java.time.LocalDateTime;

public class CartAddResponse {

    private final boolean success;
    private final String message;

    public CartAddResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }
}
