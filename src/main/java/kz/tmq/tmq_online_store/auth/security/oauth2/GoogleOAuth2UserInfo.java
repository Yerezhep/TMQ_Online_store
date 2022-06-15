package kz.tmq.tmq_online_store.auth.security.oauth2;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getFirstName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getLastName() {
        return "";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("login");
    }


}
