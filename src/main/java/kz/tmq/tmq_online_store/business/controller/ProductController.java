package kz.tmq.tmq_online_store.business.controller;

import kz.tmq.tmq_online_store.business.dto.product.*;
import kz.tmq.tmq_online_store.business.entity.Product;
import kz.tmq.tmq_online_store.business.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductFindAllResponse>> findAll() {
        List<ProductFindAllResponse> findAllResponseList = service.findAll();
        return ResponseEntity.ok(findAllResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductFindOneResponse> findById(@PathVariable Long id) {
        try {
            ProductFindOneResponse productFindOneResponse = service.findById(id);
            return ResponseEntity.ok(productFindOneResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ProductCreateResponse> create(@RequestBody ProductCreateRequest product) {
        ProductCreateResponse productCreateResponse = service.create(product);
        return new ResponseEntity(productCreateResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponse> update(@Valid @PathVariable Long id, @RequestBody ProductUpdateRequest productUpdateRequest) {
        try {
            ProductUpdateResponse updateResponse = service.update(id, productUpdateRequest);
            return new ResponseEntity(updateResponse, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
