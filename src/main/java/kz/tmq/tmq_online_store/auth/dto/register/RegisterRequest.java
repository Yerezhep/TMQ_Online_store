package kz.tmq.tmq_online_store.auth.dto.register;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
public class RegisterRequest {

    @Email
    @Length
    private String email;

    private String username;

    private String password;

    private String password2;

}
