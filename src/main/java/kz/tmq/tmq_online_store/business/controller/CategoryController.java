package kz.tmq.tmq_online_store.business.controller;

import kz.tmq.tmq_online_store.business.dto.category.CategoryCreateRequest;
import kz.tmq.tmq_online_store.business.dto.category.CategoryCreateResponse;
import kz.tmq.tmq_online_store.business.dto.category.CategoryFindOneResponse;
import kz.tmq.tmq_online_store.business.entity.Category;
import kz.tmq.tmq_online_store.business.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public List<Category> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryFindOneResponse> findById(@PathVariable Long id) {
        try {
            CategoryFindOneResponse categoryFindOneResponse = service.findById(id);
            return ResponseEntity.ok(categoryFindOneResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CategoryCreateResponse> create(@Valid @RequestBody CategoryCreateRequest createRequest) {
        CategoryCreateResponse createResponse = service.create(createRequest);
        return new ResponseEntity(createResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
