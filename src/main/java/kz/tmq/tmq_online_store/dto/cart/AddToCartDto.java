package kz.tmq.tmq_online_store.dto.cart;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddToCartDto {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

}
