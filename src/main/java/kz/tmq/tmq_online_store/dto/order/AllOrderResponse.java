package kz.tmq.tmq_online_store.dto.order;

import kz.tmq.tmq_online_store.domain.enums.OrderStatus;
import lombok.Data;

import java.util.Date;

@Data
public class AllOrderResponse {

    private Long id;

    private String orderKey;

    private Date createdDate;

    private OrderStatus orderStatus;

    private Double orderTotal;

}
