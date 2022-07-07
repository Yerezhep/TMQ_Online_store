package kz.tmq.tmq_online_store.controller.business;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.dto.cart.AddToCartDto;
import kz.tmq.tmq_online_store.dto.cart.CartDto;
import kz.tmq.tmq_online_store.dto.cart.CartItemDto;
import kz.tmq.tmq_online_store.dto.cart.CartResponse;
import kz.tmq.tmq_online_store.security.UserPrincipal;
import kz.tmq.tmq_online_store.serivce.UserService;
import kz.tmq.tmq_online_store.domain.business.Cart;
import kz.tmq.tmq_online_store.serivce.business.CartItemService;
import kz.tmq.tmq_online_store.serivce.business.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {


    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;

    public CartController(CartService cartService, CartItemService cartItemService, UserService userService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.userService = userService;
    }


    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal){

        User user = userService.findById(userPrincipal.getId());

        if(cartService.cartIsPresent(user)){
            cartService.addToExistingCart(user, addToCartDto);
        }else {
            cartService.addCartFirstTime(user, addToCartDto);
        }

        return new ResponseEntity<>(new CartResponse(true, "Added to cart"), HttpStatus.CREATED);

    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItemDto> getCartItem(@PathVariable Long cartItemId,
                                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId());
        Cart cart = cartService.getUserCart(user);
        CartItemDto cartItemDto = cartItemService.findById(cart, cartItemId);
        return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<CartDto> getCartItems(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId());
        CartDto cartDto = cartItemService.getCartItems(cartService.getUserCart(user));
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<CartResponse> removeCartItem(@PathVariable("cartItemId") Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId());
        cartItemService.removeCartIemFromCart(id, user);
        return new ResponseEntity<>(new CartResponse(true, "CartItem has been removed"), HttpStatus.OK);
    }


    @DeleteMapping("/clear")
    public ResponseEntity<CartResponse> clearCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId());
        cartService.clearShoppingCart(user);
        return new ResponseEntity<>(new CartResponse(true, "Cart has been cleared"), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponse> updateCartItem(@RequestParam("id") Long id,
                                 @RequestParam("quantity") int quantity,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId());
        cartItemService.updateCartItem(id, quantity, user);
        return new ResponseEntity<>(new CartResponse(true, "CartItem has been updated"), HttpStatus.OK);
    }


}
