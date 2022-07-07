package kz.tmq.tmq_online_store.serivce.business.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tmq.tmq_online_store.dto.product.*;
import kz.tmq.tmq_online_store.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.mapper.CommonMapper;
import kz.tmq.tmq_online_store.domain.business.Product;
import kz.tmq.tmq_online_store.domain.business.ProductImage;
import kz.tmq.tmq_online_store.repository.business.ProductImageRepository;
import kz.tmq.tmq_online_store.repository.business.ProductRepository;
import kz.tmq.tmq_online_store.serivce.business.ProductService;
import kz.tmq.tmq_online_store.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CommonMapper commonMapper;
    private final FileUploadUtil fileUploadUtil;
    private final ProductImageRepository productImageRepository;

    public ProductServiceImpl(ProductRepository productRepository, CommonMapper commonMapper, FileUploadUtil fileUploadUtil, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
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
    public ProductCreateResponse create(List<MultipartFile> files, String createRequest) {
        ProductCreateRequest productCreateRequest = new ProductCreateRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            productCreateRequest = objectMapper.readValue(createRequest, ProductCreateRequest.class);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        Product creatingProduct = commonMapper.convertTo(productCreateRequest, Product.class);
        List<ProductImage> productImages = new ArrayList<>();
        fileUploadUtil.uploadImages(files, creatingProduct, productImages);
        creatingProduct.setImages(productImages);
        Product createdProduct = productRepository.save(creatingProduct);

        ProductCreateResponse productCreateResponse = commonMapper.convertTo(createdProduct, ProductCreateResponse.class);
        return productCreateResponse;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductUpdateResponse update(Long id, String productUpdateRequest, List<MultipartFile> files) {
        Product product = productRepository.findById(id).get();
        List<ProductImage> productImages = product.getImages();
        productImageRepository.deleteAllInBatch(productImages);
        productImages.clear();

        fileUploadUtil.uploadImages(files, product, productImages);

        ProductUpdateRequest updateRequest = new ProductUpdateRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            updateRequest = objectMapper.readValue(productUpdateRequest, ProductUpdateRequest.class);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }

        Product updatingProduct = commonMapper.convertTo(updateRequest, Product.class);
        product.setTitle(updatingProduct.getTitle());
        product.setPrice(updatingProduct.getPrice());
        product.setDescription(updatingProduct.getDescription());
        product.setCategory(updatingProduct.getCategory());
        product.setImages(productImages);

        Product updatedProduct = productRepository.save(product);

        ProductUpdateResponse productUpdateResponse = commonMapper.convertTo(updatedProduct, ProductUpdateResponse.class);

        return productUpdateResponse;
    }

}
