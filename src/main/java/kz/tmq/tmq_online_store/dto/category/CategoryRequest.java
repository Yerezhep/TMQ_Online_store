package kz.tmq.tmq_online_store.dto.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryRequest {
    @NotBlank
    private String name;

}
