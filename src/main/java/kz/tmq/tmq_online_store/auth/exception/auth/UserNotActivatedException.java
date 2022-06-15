package kz.tmq.tmq_online_store.auth.exception.auth;

public class UserNotActivatedException extends RuntimeException {

    public UserNotActivatedException(String message) {
        super(message);
    }
}
