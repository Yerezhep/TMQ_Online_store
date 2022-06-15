package kz.tmq.tmq_online_store.auth.serivce;

import kz.tmq.tmq_online_store.auth.domain.Role;
import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.dto.auth.*;
import org.springframework.validation.BindingResult;

public interface AuthService {

    RegisterResponse addUser(RegisterRequest registerRequest, BindingResult bindingResult);

    String activateUser(String activationCode);

    LoginResponse login(LoginRequest loginRequest);

    User findUserByUsernameOrEmail(String usernameOrEmail);

    String sendPasswordResetCode(String email);

    String getUserByPasswordResetCode(String passwordResetCode);

    String resetPassword(ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult);

}
