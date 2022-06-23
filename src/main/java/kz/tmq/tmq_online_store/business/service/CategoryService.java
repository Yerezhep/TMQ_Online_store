package kz.tmq.tmq_online_store.business.service;

import kz.tmq.tmq_online_store.business.dto.category.*;
import kz.tmq.tmq_online_store.business.entity.Category;

import java.util.List;

public interface CategoryService {
    public CategoryCreateResponse create(CategoryCreateRequest createRequest);

    public CategoryFindOneResponse findById(Long id);

    public List<Category> findAll();

    CategoryUpdateResponse update(Long id, CategoryUpdateRequest updateRequest);

    void delete(Long id);
}
