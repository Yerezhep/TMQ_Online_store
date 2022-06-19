package kz.tmq.tmq_online_store.auth.security.oauth2.user;

import kz.tmq.tmq_online_store.auth.domain.enums.AuthProvider;
import kz.tmq.tmq_online_store.auth.exception.auth.OAuth2NoSuchRegistrationId;

import java.util.Map;

public class OAuth2UserFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GITHUB.name())) {
            return new GithubOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.name())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2NoSuchRegistrationId("Sorry, application does not support giver registration id: " + registrationId);
        }
    }

}
