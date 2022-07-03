package kz.tmq.tmq_online_store.business.dto.shippingAddress;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ShippingAddressDto {

    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String state;
    @NotBlank
    private String zipCode;
}
