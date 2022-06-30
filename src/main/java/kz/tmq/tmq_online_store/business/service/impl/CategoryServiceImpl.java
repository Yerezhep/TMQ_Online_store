package kz.tmq.tmq_online_store.business.service.impl;

import kz.tmq.tmq_online_store.auth.mapper.CommonMapper;
import kz.tmq.tmq_online_store.business.dto.category.*;
import kz.tmq.tmq_online_store.business.entity.Category;
import kz.tmq.tmq_online_store.business.repository.CategoryRepository;
import kz.tmq.tmq_online_store.business.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private CommonMapper commonMapper;

    @Override
    public CategoryCreateResponse create(CategoryCreateRequest createRequest) {
        Category creatingCategory = commonMapper.convertTo(createRequest, Category.class);
        Category createdCategory = repository.save(creatingCategory);

        CategoryCreateResponse createResponse = commonMapper.convertTo(createdCategory, CategoryCreateResponse.class);
        return createResponse;
    }

    @Override
    public CategoryFindOneResponse findById(Long id){
        Category category = repository.findById(id).get();
        CategoryFindOneResponse categoryFindOneResponse = commonMapper.convertTo(category, CategoryFindOneResponse.class);

        return categoryFindOneResponse;
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public CategoryUpdateResponse update(Long id, CategoryUpdateRequest updateRequest) {
        Category category = repository.findById(id).get();
        Category requestCategory = commonMapper.convertTo(updateRequest, Category.class);
        category.setTitle(requestCategory.getTitle());
        category.setKeywords(requestCategory.getKeywords());
        Category updatedCategory = repository.save(category);
        CategoryUpdateResponse categoryUpdateResponse = commonMapper.convertTo(updatedCategory, CategoryUpdateResponse.class);

        return categoryUpdateResponse;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }


}
