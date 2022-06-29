package kz.tmq.tmq_online_store.business.dto.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.tmq.tmq_online_store.business.entity.Product;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CategoryFindOneResponse {
    private Long id;
    private String title;
    private String keywords;
    private Date createdAt;
    private List<Product> products;

}
