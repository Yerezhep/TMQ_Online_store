package kz.tmq.tmq_online_store.auth.security.oauth2;

import kz.tmq.tmq_online_store.auth.domain.Role;
import kz.tmq.tmq_online_store.auth.domain.enums.RoleEnum;
import kz.tmq.tmq_online_store.auth.repository.RoleRepository;
import kz.tmq.tmq_online_store.auth.security.JwtProvider;
import kz.tmq.tmq_online_store.auth.serivce.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final RoleService roleService;

    @Value("$hostname}")
    private String hostname;

    public OAuth2SuccessHandler(JwtProvider jwtProvider, RoleService roleService) {
        this.jwtProvider = jwtProvider;
        this.roleService = roleService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        if (email == null) {
            email = (String) oAuth2User.getAttributes().get("login");
        }

        Role role = roleService.findRoleByName(RoleEnum.USER.name());
        String token = jwtProvider.generateToken(email, Collections.singletonList(role));
        String redirectUri = "http://" + hostname + "/oauth2/redirect";
        String uri = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }
}
