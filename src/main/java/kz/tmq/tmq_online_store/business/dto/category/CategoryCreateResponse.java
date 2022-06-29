package kz.tmq.tmq_online_store.business.dto.category;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryCreateResponse {
    private Long id;
    private String title;
    private String keywords;
    private Date createdAt;

}