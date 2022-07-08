package kz.tmq.tmq_online_store.security.oauth2;

import kz.tmq.tmq_online_store.domain.Role;
import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.domain.UserDetails;
import kz.tmq.tmq_online_store.domain.business.Cart;
import kz.tmq.tmq_online_store.domain.enums.AuthProvider;
import kz.tmq.tmq_online_store.domain.enums.RoleEnum;
import kz.tmq.tmq_online_store.repository.UserDetailsRepository;
import kz.tmq.tmq_online_store.repository.UserRepository;
import kz.tmq.tmq_online_store.repository.business.CartRepository;
import kz.tmq.tmq_online_store.security.UserPrincipal;
import kz.tmq.tmq_online_store.security.oauth2.user.OAuth2UserFactory;
import kz.tmq.tmq_online_store.security.oauth2.user.OAuth2UserInfo;
import kz.tmq.tmq_online_store.serivce.RoleService;
import kz.tmq.tmq_online_store.serivce.UserDetailsService;
import kz.tmq.tmq_online_store.serivce.UserService;
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
    private final RoleService roleService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    private final CartRepository cartRepository;

    public CustomOAuth2UserService(UserService userService, RoleService roleService, UserDetailsService userDetailsService, UserRepository userRepository, UserDetailsRepository userDetailsRepository, CartRepository cartRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.cartRepository = cartRepository;
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
        user.setUsername(oAuth2UserInfo.getUsername());
        user.setAuthProvider(AuthProvider.valueOf(provider.toUpperCase()));

        return user;
    }

    private User registerOAuth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setUsername(oAuth2UserInfo.getUsername());
        user.setActive(true);
        user.setAuthProvider(AuthProvider.valueOf(provider.toUpperCase()));
        Role role = roleService.findRoleByName(RoleEnum.ROLE_USER.name());
        user.setRoles(Collections.singletonList(role));
        user = userRepository.save(user);

        // user details and cart
        UserDetails userDetails = new UserDetails();
        userDetails.setUser(user);
        userDetailsRepository.save(userDetails);
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        return user;
    }

}
