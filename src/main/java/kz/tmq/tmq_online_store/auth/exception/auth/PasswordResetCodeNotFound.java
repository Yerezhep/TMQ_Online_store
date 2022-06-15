package kz.tmq.tmq_online_store.auth.exception.auth;

public class PasswordResetCodeNotFound extends RuntimeException {

    public PasswordResetCodeNotFound(String message) {
        super(message);
    }
}
