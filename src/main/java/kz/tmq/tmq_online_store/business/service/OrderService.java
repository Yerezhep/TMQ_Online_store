package kz.tmq.tmq_online_store.business.service;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.business.dto.order.OrderCreateRequest;
import kz.tmq.tmq_online_store.business.dto.shippingAddress.ShippingAddressDto;
import kz.tmq.tmq_online_store.business.entity.Order;
import org.springframework.validation.BindingResult;

import java.util.List;


public interface OrderService {

    Order findById(Long id);

    Order findByUser(Order order,User user);

    Order createOrder(User user, OrderCreateRequest orderCreateRequest);

    List<Order> findAllByUser(User user);

}
