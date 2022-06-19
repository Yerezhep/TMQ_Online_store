package kz.tmq.tmq_online_store.auth.security.oauth2;

import kz.tmq.tmq_online_store.auth.domain.Role;
import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.domain.enums.AuthProvider;
import kz.tmq.tmq_online_store.auth.domain.enums.RoleEnum;
import kz.tmq.tmq_online_store.auth.repository.UserRepository;
import kz.tmq.tmq_online_store.auth.security.UserPrincipal;
import kz.tmq.tmq_online_store.auth.security.oauth2.user.OAuth2UserFactory;
import kz.tmq.tmq_online_store.auth.security.oauth2.user.OAuth2UserInfo;
import kz.tmq.tmq_online_store.auth.serivce.RoleService;
import kz.tmq.tmq_online_store.auth.serivce.UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public CustomOAuth2UserService(UserService userService, UserRepository userRepository, RoleService roleService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserFactory
                .getOAuth2UserInfo(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        Optional<User> userOptional;
        if (oAuth2UserInfo.getEmail() != null) {
            userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        } else {
            userOptional = userRepository.findByUsername(oAuth2UserInfo.getUsername());
        }
        User user;
        if(userOptional.isPresent()) {
            user = updateOAuth2User(userRequest.getClientRegistration().getRegistrationId(), oAuth2UserInfo);
        } else {
            user = registerOAuth2User(userRequest.getClientRegistration().getRegistrationId(), oAuth2UserInfo);
        }

        UserPrincipal userPrincipal = UserPrincipal.create(user, oAuth2User.getAttributes());
        return userPrincipal;
    }

    private User updateOAuth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
        User user = userService.findByEmail(oAuth2UserInfo.getEmail());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setAuthProvider(AuthProvider.valueOf(provider.toUpperCase()));
        return user;
    }

    private User registerOAuth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setActive(true);
        user.setAuthProvider(AuthProvider.valueOf(provider.toUpperCase()));
        Role role = roleService.findRoleByName(RoleEnum.ROLE_USER.name());
        user.setRoles(Collections.singletonList(role));

        return userRepository.save(user);
    }

}
