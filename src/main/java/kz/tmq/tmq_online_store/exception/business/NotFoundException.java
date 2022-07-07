package kz.tmq.tmq_online_store.exception.business;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
