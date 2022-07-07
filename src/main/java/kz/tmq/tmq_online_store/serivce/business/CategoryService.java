package kz.tmq.tmq_online_store.serivce.business;

import kz.tmq.tmq_online_store.domain.business.Category;
import kz.tmq.tmq_online_store.dto.category.*;

import java.util.List;

public interface CategoryService {
    public CategoryResponse create(CategoryCreateRequest createRequest);

    public CategoryFindOneResponse findById(Long id);

    public List<Category> findAll();

    CategoryUpdateResponse update(Long id, CategoryUpdateRequest updateRequest);

    void delete(Long id);
}
