package kz.tmq.tmq_online_store.business.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.tmq.tmq_online_store.business.entity.Category;
import lombok.Data;

import java.util.Date;

@Data
public class ProductUpdateResponse {
    private Long id;

    private String title;

    private String description;

    private Double price;

    private String keywords;

    private boolean status;

    private Date createdAt;

    private Category category;


}