package kz.tmq.tmq_online_store.business.controller;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.mapper.CommonMapper;
import kz.tmq.tmq_online_store.auth.security.UserPrincipal;
import kz.tmq.tmq_online_store.auth.serivce.UserService;
import kz.tmq.tmq_online_store.business.dto.order.*;
import kz.tmq.tmq_online_store.business.entity.Order;
import kz.tmq.tmq_online_store.business.entity.OrderReceiver;
import kz.tmq.tmq_online_store.business.entity.OrderShippingAddress;
import kz.tmq.tmq_online_store.business.service.CartService;
import kz.tmq.tmq_online_store.business.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final CommonMapper commonMapper;

    public OrderController(OrderService orderService, UserService userService, CartService cartService, CommonMapper commonMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
        this.commonMapper = commonMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponseMessage> createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest,
                                                            @AuthenticationPrincipal UserPrincipal userPrincipal){

        User user = userService.findById(userPrincipal.getId());
        orderService.createOrder(user, orderCreateRequest);
        cartService.clearShoppingCart(user);

        return new ResponseEntity<>(new OrderResponseMessage(true, "Order has been created"), HttpStatus.CREATED);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable(name = "orderId") Long orderId, @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userService.findById(userPrincipal.getId());
        Order order = orderService.findById(orderId);
        Order userOrder = orderService.findByUser(order, user);

        OrderShippingAddress shippingAddress = userOrder.getShippingAddress();
        OrderReceiver orderReceiver = userOrder.getOrderReceiver();


        ShippingAddressResponse shippingAddressResponse = commonMapper.convertTo(shippingAddress, ShippingAddressResponse.class);
        OrderReceiverResponse orderReceiverResponse = commonMapper.convertTo(orderReceiver, OrderReceiverResponse.class);

        OrderResponse orderResponse = commonMapper.convertTo(userOrder, OrderResponse.class);
        orderResponse.setShippingAddressResponse(shippingAddressResponse);
        orderResponse.setOrderReceiverResponse(orderReceiverResponse);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping()
    public ResponseEntity<List<AllOrderResponse>> getOrders(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId());
        List<Order> orders = orderService.findAllByUser(user);
        return ResponseEntity.ok(commonMapper.convertToList(orders, AllOrderResponse.class));
    }



}
