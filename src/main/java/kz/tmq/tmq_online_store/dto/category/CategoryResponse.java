package kz.tmq.tmq_online_store.dto.category;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryResponse {

    private Long id;
    private String title;
    private String keywords;
    private Date createdAt;

}
