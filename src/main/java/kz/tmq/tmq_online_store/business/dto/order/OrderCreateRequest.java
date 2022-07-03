package kz.tmq.tmq_online_store.business.dto.order;

import kz.tmq.tmq_online_store.business.entity.OrderReceiver;
import kz.tmq.tmq_online_store.business.entity.OrderShippingAddress;
import lombok.Data;

import javax.persistence.Column;
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

    @NotBlank()
    private String zipCode;

}
