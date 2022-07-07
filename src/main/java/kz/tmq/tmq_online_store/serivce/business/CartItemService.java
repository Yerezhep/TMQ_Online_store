package kz.tmq.tmq_online_store.serivce.business;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.dto.cart.CartDto;
import kz.tmq.tmq_online_store.dto.cart.CartItemDto;
import kz.tmq.tmq_online_store.domain.business.Cart;


public interface CartItemService {

    CartItemDto findById(Cart cart, Long id);

    CartDto getCartItems(Cart cart);

    void removeCartIemFromCart(Long id, User user);

    void updateCartItem(Long id, int quantity, User user);

}
