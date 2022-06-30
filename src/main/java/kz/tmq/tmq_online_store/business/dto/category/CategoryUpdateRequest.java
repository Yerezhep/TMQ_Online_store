package kz.tmq.tmq_online_store.business.dto.category;

import lombok.Data;

@Data
public class CategoryUpdateRequest {
    private String title;
    private String keywords;
}
