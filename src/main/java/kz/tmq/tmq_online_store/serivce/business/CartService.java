package kz.tmq.tmq_online_store.serivce.business;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.dto.cart.AddToCartDto;
import kz.tmq.tmq_online_store.domain.business.Cart;
import kz.tmq.tmq_online_store.dto.cart.CartDto;
import kz.tmq.tmq_online_store.dto.cart.CartResponse;


public interface CartService {

    CartResponse addToCart(String email, AddToCartDto addToCartDto);

    void clearShoppingCart(User user);

    Cart saveCart(Cart cart);

}
