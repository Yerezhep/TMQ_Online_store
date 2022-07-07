package kz.tmq.tmq_online_store.dto.category;

import kz.tmq.tmq_online_store.domain.business.Product;
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
