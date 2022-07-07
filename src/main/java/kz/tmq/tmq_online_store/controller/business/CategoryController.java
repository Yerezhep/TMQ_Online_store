package kz.tmq.tmq_online_store.controller.business;

import kz.tmq.tmq_online_store.domain.business.Category;
import kz.tmq.tmq_online_store.serivce.business.CategoryService;
import kz.tmq.tmq_online_store.dto.category.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/all")
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
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryCreateRequest createRequest) {
        CategoryResponse createResponse = service.create(createRequest);
        return new ResponseEntity(createResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryUpdateResponse> update(@Valid @PathVariable Long id, @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        try {
            CategoryUpdateResponse updateResponse = service.update(id, categoryUpdateRequest);
            return new ResponseEntity(updateResponse, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
