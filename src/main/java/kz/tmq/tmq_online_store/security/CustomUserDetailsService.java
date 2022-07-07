package kz.tmq.tmq_online_store.security;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.serivce.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        User user = userService.findByEmailOrUsername(emailOrUsername);
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userService.findById(id);
        return UserPrincipal.create(user);
    }

}
