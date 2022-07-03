package kz.tmq.tmq_online_store.business.service;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.business.dto.cart.CartDto;
import kz.tmq.tmq_online_store.business.dto.cart.CartItemDto;
import kz.tmq.tmq_online_store.business.entity.Cart;
import kz.tmq.tmq_online_store.business.entity.CartItem;


public interface CartItemService {

    CartItemDto findById(Cart cart, Long id);

    CartDto getCartItems(Cart cart);

    void removeCartIemFromCart(Long id, User user);

    void updateCartItem(Long id, int quantity, User user);

}
