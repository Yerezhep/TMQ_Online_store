package kz.tmq.tmq_online_store.business.service;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.business.dto.cart.AddToCartDto;
import kz.tmq.tmq_online_store.business.entity.Cart;
import kz.tmq.tmq_online_store.business.entity.CartItem;

import java.util.Optional;
import java.util.Set;

public interface CartService {

    boolean cartIsPresent(User user);

    public Cart addCartFirstTime(User user, AddToCartDto addToCartDto);
    public Cart addToExistingCart(User user, AddToCartDto addToCartDto);

    public Cart getUserCart(User user);
    public void clearShoppingCart(User user);

}
