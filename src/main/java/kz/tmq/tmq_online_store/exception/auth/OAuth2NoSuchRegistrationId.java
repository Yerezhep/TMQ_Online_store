package kz.tmq.tmq_online_store.exception.auth;

public class OAuth2NoSuchRegistrationId extends RuntimeException {

    public OAuth2NoSuchRegistrationId(String message) {
        super(message);
    }
}