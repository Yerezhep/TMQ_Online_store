package kz.tmq.tmq_online_store.exception.business;

import lombok.Data;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException(String message) {
        super(message);
    }
}
