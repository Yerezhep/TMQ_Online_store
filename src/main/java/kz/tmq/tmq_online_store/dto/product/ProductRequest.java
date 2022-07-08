package kz.tmq.tmq_online_store.dto.product;

import lombok.Data;

@Data
public class ProductRequest {

    private String title;

    private String description;

    private Double price;

    private boolean status;

    private Long categoryId;
}
