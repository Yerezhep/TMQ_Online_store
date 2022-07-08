package kz.tmq.tmq_online_store.serivce.business.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tmq.tmq_online_store.domain.business.Category;
import kz.tmq.tmq_online_store.dto.product.*;
import kz.tmq.tmq_online_store.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.mapper.CommonMapper;
import kz.tmq.tmq_online_store.domain.business.Product;
import kz.tmq.tmq_online_store.domain.business.ProductImage;
import kz.tmq.tmq_online_store.repository.business.ProductImageRepository;
import kz.tmq.tmq_online_store.repository.business.ProductRepository;
import kz.tmq.tmq_online_store.serivce.business.CategoryService;
import kz.tmq.tmq_online_store.serivce.business.ProductService;
import kz.tmq.tmq_online_store.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CommonMapper commonMapper;
    private final FileUploadUtil fileUploadUtil;
    private final ProductImageRepository productImageRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, CommonMapper commonMapper, FileUploadUtil fileUploadUtil, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.commonMapper = commonMapper;
        this.fileUploadUtil = fileUploadUtil;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return product;
    }

    @Override
        public ProductResponse add(List<MultipartFile> files, String createRequest) {
            ProductRequest productRequest = new ProductRequest();
            ObjectMapper objectMapper = new ObjectMapper();
        try {
            productRequest = objectMapper.readValue(createRequest, ProductRequest.class);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        Product product = commonMapper.convertTo(productRequest, Product.class);
        Category category = categoryService.findById(productRequest.getCategoryId());
        product.setCategory(category);
        List<ProductImage> productImages = new ArrayList<>();
        fileUploadUtil.uploadImages(files, product, productImages);
        product.setImages(productImages);
        product = productRepository.save(product);

        ProductResponse productResponse = commonMapper.convertTo(product, ProductResponse.class);
        return productResponse;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse update(Long id, String productUpdateRequest, List<MultipartFile> files) {
        Product product = findById(id);
        List<ProductImage> productImages = product.getImages();
        productImageRepository.deleteAllInBatch(productImages);
        productImages.clear();

        fileUploadUtil.uploadImages(files, product, productImages);

        ProductRequest updateRequest = new ProductRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            updateRequest = objectMapper.readValue(productUpdateRequest, ProductRequest.class);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }

        Category category = categoryService.findById(updateRequest.getCategoryId());
        product.setTitle(updateRequest.getTitle());
        product.setPrice(updateRequest.getPrice());
        product.setDescription(updateRequest.getDescription());
        product.setCategory(category);
        product.setImages(productImages);

        product = productRepository.save(product);

        ProductResponse productResponse = commonMapper.convertTo(product, ProductResponse.class);

        return productResponse;
    }

}
