package kz.tmq.tmq_online_store.auth.exception.auth;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}
