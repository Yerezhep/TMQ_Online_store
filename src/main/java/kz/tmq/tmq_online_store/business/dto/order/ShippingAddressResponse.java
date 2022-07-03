package kz.tmq.tmq_online_store.business.dto.order;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ShippingAddressResponse {

    private String city;

    private String street;

    private String state;

    private String zipCode;

}
