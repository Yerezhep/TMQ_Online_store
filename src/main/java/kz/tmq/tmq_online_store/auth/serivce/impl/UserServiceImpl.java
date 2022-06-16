package kz.tmq.tmq_online_store.auth.serivce.impl;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.exception.auth.UserNotFoundException;
import kz.tmq.tmq_online_store.auth.repository.UserRepository;
import kz.tmq.tmq_online_store.auth.serivce.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user found: " + email));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("No user found: " + username));
    }
}
