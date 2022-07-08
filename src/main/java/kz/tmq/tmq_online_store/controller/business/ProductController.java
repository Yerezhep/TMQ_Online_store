package kz.tmq.tmq_online_store.controller.business;

import kz.tmq.tmq_online_store.domain.business.Product;
import kz.tmq.tmq_online_store.dto.product.ProductResponse;
import kz.tmq.tmq_online_store.mapper.CommonMapper;
import kz.tmq.tmq_online_store.serivce.business.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final CommonMapper commonMapper;

    public ProductController(ProductService productService, CommonMapper commonMapper) {
        this.productService = productService;
        this.commonMapper = commonMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<Product> products = productService.findAll();
        List<ProductResponse> productResponses = commonMapper.convertToList(products, ProductResponse.class);
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        ProductResponse productResponse = commonMapper.convertTo(product, ProductResponse.class);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductResponse> add(
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("product") String product) {
        ProductResponse productResponse = productService.add(files,product);
        return new ResponseEntity(productResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@Valid @PathVariable Long id,
                                                  @RequestPart("product") String productUpdateRequest,
                                                  @RequestPart("files") List<MultipartFile> files) {
        ProductResponse updateResponse = productService.update(id, productUpdateRequest,files);
        return new ResponseEntity(updateResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
