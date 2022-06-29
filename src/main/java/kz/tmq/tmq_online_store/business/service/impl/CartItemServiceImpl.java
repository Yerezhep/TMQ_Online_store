package kz.tmq.tmq_online_store.business.service.impl;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.business.dto.cart.AddToCartDto;
import kz.tmq.tmq_online_store.business.entity.Cart;
import kz.tmq.tmq_online_store.business.entity.CartItem;
import kz.tmq.tmq_online_store.business.entity.Product;
import kz.tmq.tmq_online_store.business.repository.CartItemRepository;
import kz.tmq.tmq_online_store.business.repository.CartRepository;
import kz.tmq.tmq_online_store.business.service.CartItemService;
import kz.tmq.tmq_online_store.business.service.CartService;
import kz.tmq.tmq_online_store.business.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public Set<CartItem> getCartItems(Cart cart) {
        return cartItemRepository.findAllByCart(cart);
    }

    @Override
    public String removeCartIemFromCart(Long id, User user) {
        Cart cart = cartRepository.findByUser(user);
        Set<CartItem> items = cart.getCartItems();
        CartItem cartItem = null;
        for(CartItem item : items){
            if(item.getId() == id){
                cartItem = item;
            }
        }
        items.remove(cartItem);
        cartItemRepository.delete(cartItem);
        cart.setCartItems(items);
        cartRepository.save(cart);
        return "CartItem was removed ";
    }

    @Override
    public String updateCartItem(Long id, int quantity, User user) {
        Cart cart = cartRepository.findByUser(user);
        CartItem cartItem = cartItemRepository.findById(id).get();
        if(cartItem.getCart().equals(cart)){
            cartItem.setQuantity(quantity);
            return "CartItem was updated successfully.";
        }
        return "CartItem not acceptable";
    }


}
