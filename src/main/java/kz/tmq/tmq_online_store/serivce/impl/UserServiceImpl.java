package kz.tmq.tmq_online_store.serivce.impl;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.domain.UserDetails;
import kz.tmq.tmq_online_store.domain.enums.Gender;
import kz.tmq.tmq_online_store.dto.profile.EditProfileRequest;
import kz.tmq.tmq_online_store.dto.profile.ProfileResponse;
import kz.tmq.tmq_online_store.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.mapper.CommonMapper;
import kz.tmq.tmq_online_store.repository.UserDetailsRepository;
import kz.tmq.tmq_online_store.repository.UserRepository;
import kz.tmq.tmq_online_store.serivce.UserDetailsService;
import kz.tmq.tmq_online_store.serivce.UserService;
import kz.tmq.tmq_online_store.constant.AuthConstant;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserDetailsRepository userDetailsRepository;
    private final UserDetailsService userDetailsService;

    private final CommonMapper commonMapper;

    public UserServiceImpl(UserRepository userRepository, UserDetailsRepository userDetailsRepository, UserDetailsService userDetailsService, CommonMapper commonMapper) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.userDetailsService = userDetailsService;
        this.commonMapper = commonMapper;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AuthConstant.USER, AuthConstant.ID, id));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(AuthConstant.USER, AuthConstant.EMAIL, email));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(AuthConstant.USER, AuthConstant.USERNAME, username));
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
                throw new ResourceNotFoundException(AuthConstant.USER, AuthConstant.EMAIL_USERNAME, emailOrUsername);
            }
        }
        return user;
    }

    @Override
    public User findByPasswordResetCode(String passwordResetCode) {
        return userRepository.findByPasswordResetCode(passwordResetCode)
                .orElseThrow(() -> new ResourceNotFoundException(AuthConstant.USER, AuthConstant.PASSWORD_RESET_CODE, passwordResetCode));
    }

    @Override
    public User findByActivationCode(String activationCode) {
        return userRepository.findByActivationCode(activationCode)
                .orElseThrow(() -> new ResourceNotFoundException(AuthConstant.USER, AuthConstant.ACTIVATION_CODE, activationCode));
    }

    @Override
    public ProfileResponse getProfile(String email) {
        User user = findByEmail(email);
        UserDetails userDetails = userDetailsService.findById(user.getId());

        ProfileResponse profileResponse = commonMapper.convertTo(userDetails, ProfileResponse.class);
        profileResponse.setEmail(user.getEmail());
        profileResponse.setUsername(user.getUsername());

        return profileResponse;
    }

    @Override
    public ProfileResponse editProfile(String email, EditProfileRequest editProfileRequest) {
        User user = findByEmail(email);
        user.setUsername(editProfileRequest.getUsername());
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.findById(user.getId());
        userDetails.setFirstName(StringUtils.capitalize(editProfileRequest.getFirstName()));
        userDetails.setLastName(StringUtils.capitalize(editProfileRequest.getLastName()));
        userDetails.setCity(StringUtils.capitalize(editProfileRequest.getCity()));
        userDetails.setAddress(StringUtils.capitalize(editProfileRequest.getAddress()));
        userDetails.setPhoneNumber(editProfileRequest.getPhoneNumber());
        userDetails.setGender(editProfileRequest.getGender()
                .equalsIgnoreCase(Gender.MALE.name()) ? Gender.MALE : Gender.FEMALE);
        userDetailsRepository.save(userDetails);

        ProfileResponse profileResponse = commonMapper.convertTo(userDetails, ProfileResponse.class);
        profileResponse.setEmail(user.getEmail());
        profileResponse.setUsername(user.getUsername());

        return profileResponse;
    }
}
