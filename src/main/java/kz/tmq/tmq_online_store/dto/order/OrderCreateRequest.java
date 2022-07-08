package kz.tmq.tmq_online_store.dto.order;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class OrderCreateRequest {

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String state;

    @NotBlank
    private String zipCode;

}
