package kz.tmq.tmq_online_store.business.service;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.business.dto.cart.AddToCartDto;
import kz.tmq.tmq_online_store.business.entity.Cart;
import kz.tmq.tmq_online_store.business.entity.CartItem;
import kz.tmq.tmq_online_store.business.entity.Product;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.util.List;
import java.util.Set;


public interface CartItemService {

    public Set<CartItem> getCartItems(Cart cart);

    public String removeCartIemFromCart(Long id, User user);

    public String updateCartItem(Long id, int quantity, User user);

}
