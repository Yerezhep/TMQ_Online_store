package kz.tmq.tmq_online_store.business.exception;

import lombok.Data;


@Data
public class CartEmptyException extends RuntimeException {
    public CartEmptyException(String message) {
        super(message);
    }
}
