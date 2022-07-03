package kz.tmq.tmq_online_store.business.service.impl;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.business.dto.cart.CartDto;
import kz.tmq.tmq_online_store.business.dto.cart.CartItemDto;
import kz.tmq.tmq_online_store.business.dto.cart.CartItemProductDto;
import kz.tmq.tmq_online_store.business.entity.Cart;
import kz.tmq.tmq_online_store.business.entity.CartItem;
import kz.tmq.tmq_online_store.business.exception.NotFoundException;
import kz.tmq.tmq_online_store.business.repository.CartItemRepository;
import kz.tmq.tmq_online_store.business.service.CartItemService;
import kz.tmq.tmq_online_store.business.service.CartService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {


    private final CartItemRepository cartItemRepository;
    private final CartService cartService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
    }

    @Override
    public CartItemDto findById(Cart cart, Long id) {

        CartItem cartItem =  cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", id));

        Set<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem1 = null;
        for(CartItem item : cartItems){
            if(item.equals(cartItem)){
                cartItem1 = item;
            }
        }

        if(cartItem1 == null){
            throw new ResourceNotFoundException("CartItem", "id", id);
        }

        CartItemProductDto cartItemProductDto = new CartItemProductDto();
        cartItemProductDto.setId(cartItem1.getProduct().getId());
        cartItemProductDto.setTitle(cartItem1.getProduct().getTitle());
        cartItemProductDto.setPrice(cartItem1.getProduct().getPrice());

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCartItemId(cartItem1.getId());
        cartItemDto.setQuantity(cartItem1.getQuantity());
        cartItemDto.setProduct(cartItemProductDto);
        cartItemDto.setAddedAt(cartItem1.getAddedAt());


        return cartItemDto;
    }


    @Override
    public CartDto getCartItems(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findAllByCartOrderByAddedAtDesc(cart);

        List<CartItemDto> cartItemsDto = new ArrayList<>();
        double totalCost = 0;

        for (CartItem cartItem: cartItems) {
            CartItemProductDto cartItemProductDto = new CartItemProductDto();
            cartItemProductDto.setId(cartItem.getProduct().getId());
            cartItemProductDto.setTitle(cartItem.getProduct().getTitle());
            cartItemProductDto.setPrice(cartItem.getProduct().getPrice());
            CartItemDto cartItemDto = new CartItemDto(cartItem, cartItemProductDto);
            totalCost += cartItemDto.getQuantity() * cartItem.getProduct().getPrice();
            cartItemsDto.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItemsDto);
        return cartDto;
    }



    @Override
    public void removeCartIemFromCart(Long id, User user) {

        Cart cart = cartService.getUserCart(user);
        Set<CartItem> items = cart.getCartItems();
        CartItem cartItem = null;
        for(CartItem item : items){
            if(item.getId() == id){
                cartItem = item;
            }
        }
        if(cartItem == null){
            throw new ResourceNotFoundException("CartItem", "id", id);
        }

        items.remove(cartItem);
        cartItemRepository.delete(cartItem);
        cart.setCartItems(items);
        cartService.saveCart(cart);

    }

    @Override
    public void updateCartItem(Long id, int quantity, User user) {
        Cart cart = cartService.getUserCart(user);
        Set<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = null;
        for (CartItem item: cartItems) {
            if (item.getId() == id) {
                cartItem = item;
            }
        }
        if(cartItem == null){
            throw new ResourceNotFoundException("CartItem", "id", id);
        }

        cartItem.setQuantity(quantity);
        cartItems.add(cartItem);
        cartItemRepository.save(cartItem);
        cart.setCartItems(cartItems);
        cartService.saveCart(cart);

    }


}
