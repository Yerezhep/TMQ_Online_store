package kz.tmq.tmq_online_store.auth.serivce.impl;

import kz.tmq.tmq_online_store.auth.domain.Role;
import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.domain.enums.AuthProvider;
import kz.tmq.tmq_online_store.auth.domain.enums.RoleEnum;
import kz.tmq.tmq_online_store.auth.dto.auth.*;
import kz.tmq.tmq_online_store.auth.exception.auth.*;
import kz.tmq.tmq_online_store.auth.mapper.CommonMapper;
import kz.tmq.tmq_online_store.auth.repository.RoleRepository;
import kz.tmq.tmq_online_store.auth.repository.UserRepository;
import kz.tmq.tmq_online_store.auth.security.JwtProvider;
import kz.tmq.tmq_online_store.auth.security.oauth2.OAuth2UserInfo;
import kz.tmq.tmq_online_store.auth.serivce.AuthService;
import kz.tmq.tmq_online_store.auth.serivce.RoleService;
import kz.tmq.tmq_online_store.auth.serivce.email.MailService;
import kz.tmq.tmq_online_store.auth.util.CookieUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.MailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final CommonMapper commonMapper;

    @Value("${hostname}")
    private String hostname;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder,
                           MailService mailService,
                           AuthenticationManager authenticationManager,
                           CommonMapper commonMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.commonMapper = commonMapper;
    }

    @Override
    public RegisterResponse addUser(RegisterRequest registerRequest, BindingResult bindingResult) {
        validateForms(bindingResult);
        validatePasswords(registerRequest.getPassword(), registerRequest.getPassword2());
        validateEmailAndUsername(registerRequest.getUsername(), registerRequest.getUsername());

        // create user
        User registeringUser = commonMapper.convertTo(registerRequest, User.class);
        Role role = roleService.findRoleByName(RoleEnum.USER.name());
        registeringUser.setRoles(Collections.singletonList(role));
        registeringUser.setPassword(passwordEncoder.encode(registeringUser.getPassword()));
        registeringUser.setAuthProvider(AuthProvider.LOCAL);
        registeringUser.setActivationCode(UUID.randomUUID().toString());

        // save user
        User registeredUser = userRepository.save(registeringUser);

        // send activation link to email
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("activationUrl", "http://" + hostname + "/register/activate/" + registeredUser.getActivationCode());
        sendMail(registeredUser.getEmail(), "Activation Link", "activate-user", attributes);

        // return response
        RegisterResponse registerResponse = commonMapper.convertTo(registeredUser, RegisterResponse.class);
        return registerResponse;
    }

    @Override
    public String activateUser(String activationCode) {
        User activatingUser = userRepository.findByActivationCode(activationCode)
                .orElseThrow(() -> new ActivationCodeNotFound("Activation Code not found"));
        activatingUser.setActivationCode(null);
        activatingUser.setActive(true);
        userRepository.save(activatingUser);

        return "Account was successfully activated: " + activatingUser.getEmail();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String emailOrUsername = loginRequest.getEmailOrUsername();
        String password = loginRequest.getPassword();

        // authenticate
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailOrUsername, password));
        User user = findUserByUsernameOrEmail(loginRequest.getEmailOrUsername());

        return new LoginResponse(
                user.getEmail(),
                user.getUsername(),
                user.getRoles(),
                user.getAuthProvider()
        );
    }

    @Override
    public String sendPasswordResetCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + email));
        user.setPasswordResetCode(UUID.randomUUID().toString());
        userRepository.save(user);

        // send link to reset password
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("resetPasswordUrl", "http://" + hostname + "/api/v1/auth/reset-password/" + user.getPasswordResetCode());
        sendMail(user.getEmail(), "Reset Password", "reset-password", attributes);

        return "Reset password link was sent to " + user.getEmail();
    }

    @Override
    public String getUserByPasswordResetCode(String passwordResetCode) {
        User user =  userRepository.findByPasswordResetCode(passwordResetCode)
                .orElseThrow(() -> new PasswordResetCodeNotFound("Password reset code not found"));
        return user.getEmail();
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult) {
        validatePasswords(resetPasswordRequest.getPassword(), resetPasswordRequest.getPassword2());
        User user = userRepository.findByEmail(resetPasswordRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + resetPasswordRequest.getEmail()));
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        user.setPasswordResetCode(null);
        userRepository.save(user);

        return "Password successfully changed for user: " + resetPasswordRequest.getEmail();
    }

    @Override
    public User updateOAuth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + oAuth2UserInfo.getEmail()));
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setUsername(oAuth2UserInfo.getUsername());
        user.setAuthProvider(AuthProvider.valueOf(provider.toUpperCase()));
        return user;
    }

    @Override
    public User registerOAuth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setUsername(oAuth2UserInfo.getUsername());
        user.setActive(true);
        user.setAuthProvider(AuthProvider.valueOf(provider.toUpperCase()));
        Role role = roleService.findRoleByName(RoleEnum.USER.name());
        user.setRoles(Collections.singletonList(role));

        return userRepository.save(user);
    }

    @Override
    public User findUserByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> optionalUserEmail = userRepository.findByEmail(usernameOrEmail);
        Optional<User> optionalUserUsername = userRepository.findByEmail(usernameOrEmail);
        if (optionalUserEmail.isEmpty() && optionalUserUsername.isEmpty()) {
            throw new UserNotFoundException("User not found: " + usernameOrEmail);
        }

        // check if user is activated
        User user = optionalUserEmail.isPresent() ? optionalUserEmail.get() : optionalUserUsername.get();
        if (user.getActivationCode() != null) {
            throw new UserNotActivatedException("User not activated: " + usernameOrEmail);
        }

        return user;
    }

    private void validateForms(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        if (errors.size() > 0) {
            String field = ((FieldError) errors.get(0)).getField();
            throw new FormNotValidException(StringUtils.capitalize(field) + " field is not valid");
        }
    }

    private void validatePasswords(String password, String password2) {
        if (!password.equals(password2)) {
            throw new FormNotValidException("Password field and confirm password fields do not match");
        }
    }

    private void validateEmailAndUsername(String email, String username) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailExistException("Email already exists: " + email);
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new EmailExistException("Username already exists: " + username);
        }
    }

    private void sendMail(String email, String subject, String template, Map<String, Object> attributes) {
        attributes.put("email", email);
        mailService.sendMessageHtml(email, subject, template, attributes);
    }

}
