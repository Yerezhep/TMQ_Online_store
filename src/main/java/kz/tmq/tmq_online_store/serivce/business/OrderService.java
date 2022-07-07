package kz.tmq.tmq_online_store.serivce.business;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.dto.order.OrderCreateRequest;
import kz.tmq.tmq_online_store.domain.business.Order;

import java.util.List;


public interface OrderService {

    Order findById(Long id);

    Order findByUser(Order order,User user);

    Order createOrder(User user, OrderCreateRequest orderCreateRequest);

    List<Order> findAllByUser(User user);

}
