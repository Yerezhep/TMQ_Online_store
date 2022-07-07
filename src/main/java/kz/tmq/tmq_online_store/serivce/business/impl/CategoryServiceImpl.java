package kz.tmq.tmq_online_store.serivce.business.impl;

import kz.tmq.tmq_online_store.dto.category.*;
import kz.tmq.tmq_online_store.mapper.CommonMapper;
import kz.tmq.tmq_online_store.domain.business.Category;
import kz.tmq.tmq_online_store.repository.business.CategoryRepository;
import kz.tmq.tmq_online_store.serivce.business.CategoryService;
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
    public CategoryResponse create(CategoryCreateRequest createRequest) {
        Category creatingCategory = commonMapper.convertTo(createRequest, Category.class);
        Category createdCategory = repository.save(creatingCategory);

        CategoryResponse createResponse = commonMapper.convertTo(createdCategory, CategoryResponse.class);
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
