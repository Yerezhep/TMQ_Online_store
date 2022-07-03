package kz.tmq.tmq_online_store.business.dto.order;

import kz.tmq.tmq_online_store.business.entity.OrderItem;
import kz.tmq.tmq_online_store.business.entity.OrderReceiver;
import kz.tmq.tmq_online_store.business.enums.OrderStatus;
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
