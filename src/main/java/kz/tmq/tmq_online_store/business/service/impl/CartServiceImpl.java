package kz.tmq.tmq_online_store.business.service.impl;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.mapper.CommonMapper;
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
import java.util.*;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CommonMapper mapper;




    @Override
    public boolean cartIsPresent(User user) {
        return cartRepository.existsByUser(user);
    }

    @Override
    public Cart addCartFirstTime(User user, AddToCartDto addToCartDto) {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(addToCartDto.getQuantity());
        cartItem.setProduct(productService.findOne(addToCartDto.getProductId()));
        cartItem.setCart(cart);

        cart.getCartItems().add(cartItem);
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public Cart addToExistingCart(User user, AddToCartDto addToCartDto) {
        Cart cart = cartRepository.findByUser(user);
        Product product = productService.findOne(addToCartDto.getProductId());
        boolean productDoesExistInTheCart = false;
        if(cart != null){
            Set<CartItem> cartItems = cart.getCartItems();
            for(CartItem cartItem : cartItems){
                if(cartItem.getProduct().equals(product)){
                    productDoesExistInTheCart = true;
                    cartItem.setQuantity(cartItem.getQuantity() + addToCartDto.getQuantity());
                    cart.setCartItems(cartItems);
                    return cartRepository.saveAndFlush(cart);
                }
            }
        }

        if(!productDoesExistInTheCart && (cart != null)){
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(addToCartDto.getQuantity());
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
            return cartRepository.saveAndFlush(cart);
        }
        return this.addCartFirstTime(user, addToCartDto);
    }

    @Override
    public Cart getUserCart(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public void clearShoppingCart(User user) {
        Cart cart = cartRepository.findByUser(user);
        cartRepository.delete(cart);
    }


}
