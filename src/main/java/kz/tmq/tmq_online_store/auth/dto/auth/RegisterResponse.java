package kz.tmq.tmq_online_store.auth.dto.auth;

import kz.tmq.tmq_online_store.auth.domain.enums.AuthProvider;
import lombok.Data;

@Data
public class RegisterResponse {

    private String email;

    private String username;

    private AuthProvider authProvider;

    private String activationCode;

}
