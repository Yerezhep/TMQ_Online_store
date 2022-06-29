package kz.tmq.tmq_online_store.business.controller;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.security.UserPrincipal;
import kz.tmq.tmq_online_store.auth.serivce.UserService;
import kz.tmq.tmq_online_store.business.dto.cart.AddToCartDto;
import kz.tmq.tmq_online_store.business.dto.cart.CartAddResponse;
import kz.tmq.tmq_online_store.business.entity.Cart;
import kz.tmq.tmq_online_store.business.entity.CartItem;
import kz.tmq.tmq_online_store.business.service.CartItemService;
import kz.tmq.tmq_online_store.business.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;



    @PostMapping("/add")
    public ResponseEntity<CartAddResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                            @AuthenticationPrincipal UserPrincipal userPrincipal){

        User user = userService.findById(userPrincipal.getId());

        if(cartService.cartIsPresent(user)){
            cartService.addToExistingCart(user, addToCartDto);
        }else {
            cartService.addCartFirstTime(user, addToCartDto);
        }
        return new ResponseEntity<>(new CartAddResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Set<CartItem>> getCartItems(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId());
        return ResponseEntity.ok(cartItemService.getCartItems(cartService.getUserCart(user)));
    }


    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable("id") Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId());
        return cartItemService.removeCartIemFromCart(id, user);
    }

    @GetMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId());
        cartService.clearShoppingCart(user);
        return "Cart was cleared";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("id") Long id,
                                 @RequestParam("quantity") int quantity,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId());
        return cartItemService.updateCartItem(id, quantity, user);
    }


}
