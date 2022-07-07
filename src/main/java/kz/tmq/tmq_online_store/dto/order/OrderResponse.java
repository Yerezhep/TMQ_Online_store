package kz.tmq.tmq_online_store.dto.order;

import kz.tmq.tmq_online_store.domain.business.OrderItem;
import kz.tmq.tmq_online_store.domain.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OrderResponse {

    private Date createdDate;

    private Set<OrderItem> orderItems;

    private OrderStatus orderStatus;

    private ShippingAddressResponse shippingAddressResponse;

    private OrderReceiverResponse orderReceiverResponse;

    private Double orderTotal;

}
