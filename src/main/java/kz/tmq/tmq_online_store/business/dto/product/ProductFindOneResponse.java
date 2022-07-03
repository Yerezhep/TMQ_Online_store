package kz.tmq.tmq_online_store.business.dto.product;

import kz.tmq.tmq_online_store.business.entity.Category;
import kz.tmq.tmq_online_store.business.entity.ProductImage;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductFindOneResponse {
    private Long id;

    private String title;

    private String description;

    private Double price;

    private String keywords;

    private boolean status;

    private Date createdAt;

    private Category category;

    private List<ProductImage> images;


}
