package kz.tmq.tmq_online_store.auth.dto.auth;

import kz.tmq.tmq_online_store.auth.domain.Role;
import kz.tmq.tmq_online_store.auth.domain.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class LoginResponse {

    private String email;

    private String username;

    private List<Role> roles;

    private AuthProvider provider;

}
