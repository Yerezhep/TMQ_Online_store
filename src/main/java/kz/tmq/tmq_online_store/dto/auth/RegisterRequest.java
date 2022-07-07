package kz.tmq.tmq_online_store.dto.auth;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 5, max = 30)
    private String username;

    @NotBlank
    @Length(min = 6, max = 30)
    private String password;

    @NotBlank
    private String password2;

}
