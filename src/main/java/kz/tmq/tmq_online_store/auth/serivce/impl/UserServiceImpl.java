package kz.tmq.tmq_online_store.auth.serivce.impl;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.auth.repository.UserRepository;
import kz.tmq.tmq_online_store.auth.serivce.UserService;
import org.springframework.stereotype.Service;

import static kz.tmq.tmq_online_store.auth.constant.AuthConstant.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, id));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER, EMAIL, email));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER, USERNAME, username));
    }

    @Override
    public User findByEmailOrUsername(String emailOrUsername) {
        User user = null;
        try {
            user = findByEmail(emailOrUsername);
        } catch (ResourceNotFoundException exception1) {
            try {
                user = findByUsername(emailOrUsername);
            } catch (ResourceNotFoundException exception2) {
                throw new ResourceNotFoundException(USER, EMAIL_USERNAME, emailOrUsername);
            }
        }
        return user;
    }

    @Override
    public User findByPasswordResetCode(String passwordResetCode) {
        return userRepository.findByPasswordResetCode(passwordResetCode)
                .orElseThrow(() -> new ResourceNotFoundException(USER, PASSWORD_RESET_CODE, passwordResetCode));
    }

    @Override
    public User findByActivationCode(String activationCode) {
        return userRepository.findByActivationCode(activationCode)
                .orElseThrow(() -> new ResourceNotFoundException(USER, ACTIVATION_CODE, activationCode));
    }
}
