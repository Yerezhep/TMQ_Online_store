package kz.tmq.tmq_online_store.controller.business;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.mapper.CommonMapper;
import kz.tmq.tmq_online_store.security.UserPrincipal;
import kz.tmq.tmq_online_store.serivce.UserService;
import kz.tmq.tmq_online_store.domain.business.Order;
import kz.tmq.tmq_online_store.domain.business.OrderReceiver;
import kz.tmq.tmq_online_store.domain.business.OrderShippingAddress;
import kz.tmq.tmq_online_store.serivce.business.CartService;
import kz.tmq.tmq_online_store.serivce.business.OrderService;
import kz.tmq.tmq_online_store.dto.order.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@PreAuthorize("hasRole('ROLE_USER')")
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

    @PostMapping
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
