package kz.tmq.tmq_online_store.dto.order;

import lombok.Data;

@Data
public class OrderResponseMessage {

    private final boolean success;
    private final String message;

    public OrderResponseMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
