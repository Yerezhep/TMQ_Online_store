package kz.tmq.tmq_online_store.business.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data

public class AddToCartDto {

    private @NotNull Long productId;
    private @NotNull Integer quantity;

}
