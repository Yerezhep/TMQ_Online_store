package kz.tmq.tmq_online_store.serivce.business.impl;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.dto.cart.AddToCartDto;
import kz.tmq.tmq_online_store.domain.business.Cart;
import kz.tmq.tmq_online_store.domain.business.CartItem;
import kz.tmq.tmq_online_store.domain.business.Product;
import kz.tmq.tmq_online_store.dto.cart.CartResponse;
import kz.tmq.tmq_online_store.repository.business.CartItemRepository;
import kz.tmq.tmq_online_store.repository.business.CartRepository;
import kz.tmq.tmq_online_store.serivce.UserService;
import kz.tmq.tmq_online_store.serivce.business.CartService;
import kz.tmq.tmq_online_store.serivce.business.ProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService, UserService userService, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartResponse addToCart(String email, AddToCartDto addToCartDto) {
        User user = userService.findByEmail(email);
        Cart cart = user.getCart();
        Product product = productService.findById(addToCartDto.getProductId());

        boolean productExist = false;
        Set<CartItem> cartItems = cart.getCartItems();
        for(CartItem cartItem : cartItems){
            if(cartItem.getProduct().equals(product)){
                productExist = true;
                cartItem.setQuantity(cartItem.getQuantity() + addToCartDto.getQuantity());
                cart.setCartItems(cartItems);
                cartRepository.save(cart);
            }
        }

        if(!productExist){
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(addToCartDto.getQuantity());
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
            cartRepository.save(cart);
        }
        return new CartResponse(true, "Product with id " + addToCartDto.getProductId() + " added successfully to cart");
    }

    @Override
    public void clearShoppingCart(User user) {
        Cart cart = user.getCart();
        Set<CartItem> cartItems = cart.getCartItems();
        cartItemRepository.deleteAllInBatch(cartItems);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

}
