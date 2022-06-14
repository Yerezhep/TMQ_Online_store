package kz.tmq.tmq_online_store.auth.serivce;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.dto.register.RegisterRequest;
import kz.tmq.tmq_online_store.auth.dto.register.RegisterResponse;
import org.springframework.validation.BindingResult;

public interface AuthService {

    RegisterResponse addUser(RegisterRequest registerRequest, BindingResult bindingResult);

}
