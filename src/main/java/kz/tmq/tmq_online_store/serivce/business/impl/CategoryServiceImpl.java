package kz.tmq.tmq_online_store.serivce.business.impl;

import kz.tmq.tmq_online_store.dto.category.*;
import kz.tmq.tmq_online_store.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.mapper.CommonMapper;
import kz.tmq.tmq_online_store.domain.business.Category;
import kz.tmq.tmq_online_store.repository.business.CategoryRepository;
import kz.tmq.tmq_online_store.serivce.business.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CommonMapper commonMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CommonMapper commonMapper) {
        this.categoryRepository = categoryRepository;
        this.commonMapper = commonMapper;
    }

    @Override
    public Category add(CategoryCreateRequest createRequest) {
        Category category = new Category();
        category.setName(StringUtils.capitalize(createRequest.getName()));
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return category;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Long id, CategoryCreateRequest categoryCreateRequest) {
        Category category = findById(id);
        category.setName(StringUtils.capitalize(categoryCreateRequest.getName()));

        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }


}
