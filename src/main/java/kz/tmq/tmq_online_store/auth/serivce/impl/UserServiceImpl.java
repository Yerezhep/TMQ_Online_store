package kz.tmq.tmq_online_store.auth.serivce.impl;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.domain.UserDetails;
import kz.tmq.tmq_online_store.auth.dto.user.UserInfoResponse;
import kz.tmq.tmq_online_store.auth.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.auth.repository.UserRepository;
import kz.tmq.tmq_online_store.auth.serivce.UserDetailsService;
import kz.tmq.tmq_online_store.auth.serivce.UserService;
import org.springframework.stereotype.Service;

import static kz.tmq.tmq_online_store.auth.constant.AuthConstant.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public UserServiceImpl(UserRepository userRepository, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
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

    @Override
    public UserInfoResponse getUserInfo(String email) {
        User user = findByEmail(email);
        UserDetails userDetails = userDetailsService.findById(user.getId());
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setEmail(user.getEmail());
        userInfoResponse.setUsername(user.getUsername());
        userInfoResponse.setFirstName(userDetails.getFirstName());
        userInfoResponse.setLastName(userDetails.getLastName());
        userInfoResponse.setGender(userDetails.getGender());
        userInfoResponse.setCity(userDetails.getCity());
        userInfoResponse.setAddress(userDetails.getAddress());
        userInfoResponse.setPhoneNumber(userDetails.getPhoneNumber());

        return userInfoResponse;
    }
}
