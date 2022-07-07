package kz.tmq.tmq_online_store.dto.product;

import kz.tmq.tmq_online_store.domain.business.Category;
import lombok.Data;

import java.util.Date;

@Data
public class ProductCreateResponse {
    private Long id;

    private String title;

    private String description;

    private Double price;

    private String keywords;

    private boolean status;

    private Date createdAt;

    private Category category;


}
