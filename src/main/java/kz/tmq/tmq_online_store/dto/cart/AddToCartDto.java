package kz.tmq.tmq_online_store.dto.cart;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddToCartDto {

    private @NotNull Long productId;
    private @NotNull Integer quantity;

}
