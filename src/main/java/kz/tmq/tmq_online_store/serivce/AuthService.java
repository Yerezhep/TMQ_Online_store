package kz.tmq.tmq_online_store.serivce;

import kz.tmq.tmq_online_store.dto.auth.*;
import org.springframework.validation.BindingResult;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest, BindingResult bindingResult);

    String activate(String activationCode);

    LoginResponse login(LoginRequest loginRequest);

    String sendPasswordResetCode(String email);

    String findByResetPasswordCode(String passwordResetCode);

    String resetPassword(ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult);

}
