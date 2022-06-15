package kz.tmq.tmq_online_store.auth.exception.auth;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String message) {
        super(message);
    }

}
