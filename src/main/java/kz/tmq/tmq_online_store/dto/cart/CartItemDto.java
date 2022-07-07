package kz.tmq.tmq_online_store.dto.cart;

import kz.tmq.tmq_online_store.domain.business.CartItem;
import lombok.Data;

import java.util.Date;

@Data
public class CartItemDto {

    private Long cartItemId;

    private CartItemProductDto product;

    private int quantity;

    private Date addedAt;

    public CartItemDto(){

    }

    public CartItemDto(CartItem cartItem, CartItemProductDto productDto) {
        this.quantity = cartItem.getQuantity();
        this.cartItemId = cartItem.getId();
        this.product = productDto;
        this.addedAt = cartItem.getAddedAt();
    }
}
