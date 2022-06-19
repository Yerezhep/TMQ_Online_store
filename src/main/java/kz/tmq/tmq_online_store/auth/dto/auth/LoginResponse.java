package kz.tmq.tmq_online_store.auth.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.tmq.tmq_online_store.auth.domain.Role;
import kz.tmq.tmq_online_store.auth.domain.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.util.List;

@AllArgsConstructor
@Data
public class LoginResponse {

    private String email;

    private String username;

    private List<Role> roles;

    private AuthProvider provider;

    @JsonIgnore
    private String token;

    @JsonIgnore
    private HttpHeaders httpHeaders;

}
