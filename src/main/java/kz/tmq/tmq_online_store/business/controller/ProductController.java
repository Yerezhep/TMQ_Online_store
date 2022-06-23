package kz.tmq.tmq_online_store.business.controller;

import kz.tmq.tmq_online_store.business.dto.product.ProductCreateRequest;
import kz.tmq.tmq_online_store.business.dto.product.ProductCreateResponse;
import kz.tmq.tmq_online_store.business.entity.Product;
import kz.tmq.tmq_online_store.business.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<ProductCreateResponse> create(@RequestBody ProductCreateRequest product) {
        ProductCreateResponse productCreateResponse = service.create(product);
        return new ResponseEntity(productCreateResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
