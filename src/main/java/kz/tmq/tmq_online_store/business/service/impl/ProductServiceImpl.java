package kz.tmq.tmq_online_store.business.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tmq.tmq_online_store.auth.mapper.CommonMapper;
import kz.tmq.tmq_online_store.business.dto.product.*;
import kz.tmq.tmq_online_store.business.entity.Product;
import kz.tmq.tmq_online_store.business.entity.ProductImage;
import kz.tmq.tmq_online_store.business.repository.ProductImageRepository;
import kz.tmq.tmq_online_store.business.repository.ProductRepository;
import kz.tmq.tmq_online_store.business.service.ProductService;
import kz.tmq.tmq_online_store.business.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private ProductImageRepository productImageRepository;
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
        Product createdProduct = repository.save(creatingProduct);

        ProductCreateResponse productCreateResponse = commonMapper.convertTo(createdProduct, ProductCreateResponse.class);
        return productCreateResponse;
    }


    @Override
    public Product findOne(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public ProductFindOneResponse findById(Long id) {
        Product product = repository.findById(id).get();
        ProductFindOneResponse productFindOneResponse = commonMapper.convertTo(product, ProductFindOneResponse.class);
        return productFindOneResponse;
    }

    @Override
    public List<ProductFindAllResponse> findAll() {
        List<Product> products = repository.findAll();
        List<ProductFindAllResponse> productFindAllResponses= new ArrayList<>();
        for (Product product : products) {
            ProductFindAllResponse productFindAllResponse = commonMapper.convertTo(product, ProductFindAllResponse.class);
            productFindAllResponses.add(productFindAllResponse);
        }
        return productFindAllResponses;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ProductUpdateResponse update(Long id, String productUpdateRequest, List<MultipartFile> files) {
        Product product = repository.findById(id).get();
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
        product.setKeywords(updatingProduct.getKeywords());
        product.setPrice(updatingProduct.getPrice());
        product.setDescription(updatingProduct.getDescription());
        product.setCategory(updatingProduct.getCategory());
        product.setImages(productImages);

        Product updatedProduct = repository.save(product);

        ProductUpdateResponse productUpdateResponse = commonMapper.convertTo(updatedProduct, ProductUpdateResponse.class);

        return productUpdateResponse;
    }

}
