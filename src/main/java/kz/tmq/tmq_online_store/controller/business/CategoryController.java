package kz.tmq.tmq_online_store.controller.business;

import kz.tmq.tmq_online_store.domain.business.Category;
import kz.tmq.tmq_online_store.serivce.business.CategoryService;
import kz.tmq.tmq_online_store.dto.category.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findById(@PathVariable Long categoryId) {
        Category category = categoryService.findById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Category> add(@Valid @RequestBody CategoryRequest createRequest) {
        Category category = categoryService.add(createRequest);
        return new ResponseEntity(category, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/edit/{categoryId}")
    public ResponseEntity<Category> update(@Valid @PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.update(categoryId, categoryRequest);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity delete(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
