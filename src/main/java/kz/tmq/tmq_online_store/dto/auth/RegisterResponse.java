package kz.tmq.tmq_online_store.dto.auth;

import kz.tmq.tmq_online_store.domain.enums.AuthProvider;
import lombok.Data;

@Data
public class RegisterResponse {

    private String email;

    private String username;

    private AuthProvider authProvider;

    private String activationCode;

}
