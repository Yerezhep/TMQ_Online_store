package kz.tmq.tmq_online_store.serivce.business;

import kz.tmq.tmq_online_store.domain.business.Category;
import kz.tmq.tmq_online_store.dto.category.*;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();
    Category add(CategoryRequest createRequest);

    Category findById(Long id);

    Category update(Long id, CategoryRequest categoryRequest);

    void delete(Long id);
}
