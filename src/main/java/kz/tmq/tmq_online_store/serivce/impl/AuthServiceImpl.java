package kz.tmq.tmq_online_store.serivce.impl;

import kz.tmq.tmq_online_store.domain.Role;
import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.domain.UserDetails;
import kz.tmq.tmq_online_store.domain.business.Cart;
import kz.tmq.tmq_online_store.domain.enums.AuthProvider;
import kz.tmq.tmq_online_store.domain.enums.RoleEnum;
import kz.tmq.tmq_online_store.dto.auth.*;
import kz.tmq.tmq_online_store.mapper.CommonMapper;
import kz.tmq.tmq_online_store.repository.UserDetailsRepository;
import kz.tmq.tmq_online_store.repository.UserRepository;
import kz.tmq.tmq_online_store.repository.business.CartRepository;
import kz.tmq.tmq_online_store.security.TokenProvider;
import kz.tmq.tmq_online_store.serivce.AuthService;
import kz.tmq.tmq_online_store.serivce.RoleService;
import kz.tmq.tmq_online_store.serivce.UserService;
import kz.tmq.tmq_online_store.util.CookieUtils;
import kz.tmq.tmq_online_store.constant.AuthConstant;
import kz.tmq.tmq_online_store.exception.auth.FormNotValidException;
import kz.tmq.tmq_online_store.exception.auth.ResourceExistException;
import kz.tmq.tmq_online_store.exception.auth.UserNotActivatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    private final CartRepository cartRepository;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailServiceImpl mailServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final CommonMapper commonMapper;

    @Autowired
    private CookieUtils cookieUtils;

    public AuthServiceImpl(UserRepository userRepository,
                           UserDetailsRepository userDetailsRepository,
                           CartRepository cartRepository, RoleService roleService,
                           UserService userService,
                           PasswordEncoder passwordEncoder,
                           MailServiceImpl mailServiceImpl,
                           AuthenticationManager authenticationManager,
                           TokenProvider tokenProvider,
                           CommonMapper commonMapper) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.cartRepository = cartRepository;
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.mailServiceImpl = mailServiceImpl;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.commonMapper = commonMapper;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest, BindingResult bindingResult) {
        // validate
        validateForms(bindingResult);
        validatePasswords(registerRequest.getPassword(), registerRequest.getPassword2());
        validateEmailAndUsername(registerRequest.getEmail(), registerRequest.getUsername());

        // create user
        User registeringUser = commonMapper.convertTo(registerRequest, User.class);
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findRoleByName(RoleEnum.ROLE_USER.name()));
        if (registerRequest.getUsername().equals("admin")) {
            roles.add(roleService.findRoleByName(RoleEnum.ROLE_ADMIN.name()));
        }
        registeringUser.setRoles(roles);
        registeringUser.setPassword(passwordEncoder.encode(registeringUser.getPassword()));
        registeringUser.setAuthProvider(AuthProvider.LOCAL);
        registeringUser.setActivationCode(UUID.randomUUID().toString());

        // save user and user details and cart
        User registeredUser = userRepository.save(registeringUser);
        UserDetails userDetails = new UserDetails();
        userDetails.setUser(registeredUser);
        userDetailsRepository.save(userDetails);
        Cart cart = new Cart();
        cart.setUser(registeredUser);
        cartRepository.save(cart);

        // send activation link to email
        sendEmail(registeredUser.getEmail(),
                AuthConstant.ACTIVATE_SUBJECT,
                AuthConstant.ACTIVATE_TEMPLATE_NAME,
                AuthConstant.ACTIVATE_ATTRIBUTE,
                AuthConstant.ACTIVATE_URL + registeredUser.getActivationCode());

        // return response
        RegisterResponse registerResponse = commonMapper.convertTo(registeredUser, RegisterResponse.class);
        return registerResponse;
    }

    @Override
    public String activate(String activationCode) {
        User activatingUser = userService.findByActivationCode(activationCode);
        activatingUser.setActivationCode(null);
        activatingUser.setActive(true);
        userRepository.save(activatingUser);

        return String.format(AuthConstant.ACCOUNT_SUCCESSFULLY_ACTIVATED_MESSAGE, activatingUser.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.findByEmailOrUsername(loginRequest.getEmailOrUsername());
        if (!user.isActive()) {
            throw new UserNotActivatedException(String.format(AuthConstant.USER_NOT_ACTIVATED_MESSAGE, user.getEmail()));
        }
        // generate token
        String token = tokenProvider.createToken(authentication);

        // jwt cookie
        HttpCookie httpCookie = cookieUtils.createJwtCookie(token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, httpCookie.toString());

        return new LoginResponse(
                user.getEmail(),
                user.getUsername(),
                user.getRoles(),
                user.getAuthProvider(),
                token,
                httpHeaders
        );
    }

    @Override
    public String sendPasswordResetCode(String email) {
        User user = userService.findByEmail(email);
        user.setPasswordResetCode(UUID.randomUUID().toString());
        userRepository.save(user);

        // send link to reset password
        sendEmail(
                user.getEmail(),
                AuthConstant.RESET_PASSWORD_SUBJECT,
                AuthConstant.RESET_PASSWORD_TEMPLATE_NAME,
                AuthConstant.RESET_PASSWORD_ATTRIBUTE,
                AuthConstant.RESET_PASSWORD_URL + user.getPasswordResetCode()
        );

        return String.format(AuthConstant.RESET_PASSWORD_LINK_SEND_MESSAGE, user.getEmail());
    }

    @Override
    public String findByResetPasswordCode(String passwordResetCode) {
        User user =  userService.findByPasswordResetCode(passwordResetCode);
        return user.getEmail();
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult) {
        validatePasswords(resetPasswordRequest.getPassword(), resetPasswordRequest.getPassword2());
        User user = userService.findByEmail(resetPasswordRequest.getEmail());
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        user.setPasswordResetCode(null);
        userRepository.save(user);

        return String.format(AuthConstant.PASSWORD_SUCCESSFULLY_CHANGED_MESSAGE, resetPasswordRequest.getEmail());
    }

    private void validateForms(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        if (errors.size() > 0) {
            String field = ((FieldError) errors.get(0)).getField();
            throw new FormNotValidException(String.format(AuthConstant.FIELD_IS_NOT_VALID_MESSAGE, StringUtils.capitalize(field)));
        }
    }

    private void validatePasswords(String password, String password2) {
        if (!password.equals(password2)) {
            throw new FormNotValidException(AuthConstant.PASSWORDS_DO_NOT_MATCH_MESSAGE);
        }
    }

    private void validateEmailAndUsername(String email, String username) {
        if (userRepository.existsByEmail(email)) {
            throw new ResourceExistException(AuthConstant.USER, AuthConstant.EMAIL, email);
        }
        if (userRepository.existsByUsername(username)) {
            throw new ResourceExistException(AuthConstant.USER, AuthConstant.USERNAME, username);
        }
    }

    private void sendEmail(String email, String subject, String template, String urlAttribute, String urlPath) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(AuthConstant.EMAIL, email);
        attributes.put(urlAttribute, urlPath);
        mailServiceImpl.sendMessageHtml(email, subject, template, attributes);
    }

}
