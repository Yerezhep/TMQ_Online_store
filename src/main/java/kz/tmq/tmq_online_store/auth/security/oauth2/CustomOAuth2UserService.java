package kz.tmq.tmq_online_store.auth.security.oauth2;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.exception.auth.UserNotFoundException;
import kz.tmq.tmq_online_store.auth.security.UserPrincipal;
import kz.tmq.tmq_online_store.auth.serivce.AuthService;
import kz.tmq.tmq_online_store.auth.serivce.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AuthService authService;
    private final UserService userService;

    public CustomOAuth2UserService(@Lazy AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());

        User user = new User();
        try {
            if (oAuth2UserInfo.getEmail() != null) {
                userService.findUserByEmail(oAuth2UserInfo.getEmail());
            } else {
                userService.findUserByUsername(oAuth2UserInfo.getUsername());
            }
            user = authService.updateOAuth2User(provider, oAuth2UserInfo);
        } catch (UserNotFoundException exception) {
            user = authService.registerOAuth2User(provider, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

}
