package kz.tmq.tmq_online_store.business.service;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.business.dto.cart.AddToCartDto;
import kz.tmq.tmq_online_store.business.entity.Cart;


public interface CartService {

    boolean cartIsPresent(User user);

    Cart addCartFirstTime(User user, AddToCartDto addToCartDto);

    Cart addToExistingCart(User user, AddToCartDto addToCartDto);

    Cart getUserCart(User user);

    void clearShoppingCart(User user);

    Cart saveCart(Cart cart);

}
