package kz.tmq.tmq_online_store.dto.product;

import kz.tmq.tmq_online_store.domain.business.Category;
import kz.tmq.tmq_online_store.domain.business.ProductImage;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductResponse {

    private Long id;

    private String title;

    private String description;

    private Double price;

    private boolean status;

    private Date createdAt;

    private Category category;

    private List<ProductImage> images;


}
