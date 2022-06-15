package kz.tmq.tmq_online_store.auth.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {

    private String emailOrUsername;

    private String password;

}
