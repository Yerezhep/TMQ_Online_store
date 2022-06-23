package kz.tmq.tmq_online_store.business.dto.product;

import kz.tmq.tmq_online_store.business.entity.Category;
import lombok.Data;

@Data
public class ProductCreateResponse {
    private Long id;

    private String title;

    private String description;

    private Double price;

    private String keywords;

    private boolean status;

    private Category category;
}
