package kz.tmq.tmq_online_store.serivce.business.impl;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.domain.business.*;
import kz.tmq.tmq_online_store.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.dto.order.OrderCreateRequest;
import kz.tmq.tmq_online_store.exception.business.NotFoundException;
import kz.tmq.tmq_online_store.repository.business.OrderItemRepository;
import kz.tmq.tmq_online_store.repository.business.OrderRepository;
import kz.tmq.tmq_online_store.serivce.business.CartService;
import kz.tmq.tmq_online_store.serivce.business.OrderService;
import kz.tmq.tmq_online_store.util.RandomStringGenerator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static kz.tmq.tmq_online_store.domain.enums.OrderStatus.NEW;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }

    @Override
    public Order findByUser(Order order, User user) {

        List<Order> userOrders = orderRepository.findAllByUser(user);
        Order userOrder = null;
        for(Order o : userOrders){
            if(o.equals(order)){
                userOrder = o;
            }
        }

        if(userOrder == null){
            throw new NotFoundException("Order doesn't exist");
        }

        return userOrder;
    }

    @Override
    public Order createOrder(User user, OrderCreateRequest orderCreateRequest) {

        Cart cart = user.getCart();
        Order order = new Order();

        OrderShippingAddress shippingAddress = new OrderShippingAddress();
        shippingAddress.setCity(orderCreateRequest.getCity());
        shippingAddress.setStreet(orderCreateRequest.getStreet());
        shippingAddress.setZipCode(orderCreateRequest.getZipCode());
        shippingAddress.setState(orderCreateRequest.getState());

        OrderReceiver orderReceiver = new OrderReceiver();
        orderReceiver.setName(orderCreateRequest.getName());
        orderReceiver.setEmail(orderCreateRequest.getEmail());
        orderReceiver.setPhoneNumber(orderCreateRequest.getPhoneNumber());

        order.setShippingAddress(shippingAddress);
        order.setOrderReceiver(orderReceiver);
        order.setOrderStatus(NEW);
        order.setOrderTotal(cart.getTotalPrice());
        order.setUser(user);

        Set<CartItem> cartItems = cart.getCartItems();
        Set<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .order(order)
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .unitPrice(cartItem.getProduct().getPrice())
                        .build())
                .collect(Collectors.toSet());

        order.setOrderItems(orderItems);
        shippingAddress.setOrder(order);
        orderReceiver.setOrder(order);

        for(CartItem cartItem : cartItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItemRepository.save(orderItem);
        }

        order.setOrderKey(RandomStringGenerator.nextSessionId());

        return orderRepository.save(order);

    }

    @Override
    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }


}
