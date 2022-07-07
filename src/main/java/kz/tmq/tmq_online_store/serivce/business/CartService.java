package kz.tmq.tmq_online_store.serivce.business;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.dto.cart.AddToCartDto;
import kz.tmq.tmq_online_store.domain.business.Cart;


public interface CartService {

    boolean cartIsPresent(User user);

    Cart addCartFirstTime(User user, AddToCartDto addToCartDto);

    Cart addToExistingCart(User user, AddToCartDto addToCartDto);

    Cart getUserCart(User user);

    void clearShoppingCart(User user);

    Cart saveCart(Cart cart);

}
