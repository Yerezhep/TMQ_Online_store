package kz.tmq.tmq_online_store.auth.exception.auth;

public class UsernameExistException extends RuntimeException {

    public UsernameExistException(String message) {
        super(message);
    }

}
