package kz.tmq.tmq_online_store.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {

    private String emailOrUsername;

    private String password;

}
