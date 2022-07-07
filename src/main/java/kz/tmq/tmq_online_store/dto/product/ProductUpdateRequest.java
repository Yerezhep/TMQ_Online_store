package kz.tmq.tmq_online_store.dto.product;

import kz.tmq.tmq_online_store.domain.business.Category;
import lombok.Data;

@Data
public class ProductUpdateRequest {
    private String title;

    private String description;

    private Double price;

    private String keywords;

    private boolean status;

    private Category category;
}
