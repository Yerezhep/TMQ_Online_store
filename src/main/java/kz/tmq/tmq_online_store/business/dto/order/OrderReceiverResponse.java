package kz.tmq.tmq_online_store.business.dto.order;

import lombok.Data;


@Data
public class OrderReceiverResponse {

    private String phoneNumber;

    private String name;

    private String email;

}
