package kz.tmq.tmq_online_store.business.dto.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryCreateRequest {
    @NotBlank
    private String title;
    private String keywords;
}
