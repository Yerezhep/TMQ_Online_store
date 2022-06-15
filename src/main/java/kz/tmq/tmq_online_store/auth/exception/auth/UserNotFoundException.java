package kz.tmq.tmq_online_store.auth.exception.auth;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
