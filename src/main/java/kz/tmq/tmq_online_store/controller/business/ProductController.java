package kz.tmq.tmq_online_store.controller.business;

import kz.tmq.tmq_online_store.domain.business.Product;
import kz.tmq.tmq_online_store.dto.product.ProductCreateResponse;
import kz.tmq.tmq_online_store.dto.product.ProductFindAllResponse;
import kz.tmq.tmq_online_store.dto.product.ProductFindOneResponse;
import kz.tmq.tmq_online_store.dto.product.ProductUpdateResponse;
import kz.tmq.tmq_online_store.serivce.business.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findById(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(name = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> create(
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("product") String product) {
        ProductCreateResponse productCreateResponse = productService.create(files,product);
        return new ResponseEntity(productCreateResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponse> update(@Valid @PathVariable Long id,
                                                        @RequestPart("product") String productUpdateRequest,
                                                        @RequestPart("files") List<MultipartFile> files) {
        try {
            ProductUpdateResponse updateResponse = productService.update(id, productUpdateRequest,files);
            return new ResponseEntity(updateResponse, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
