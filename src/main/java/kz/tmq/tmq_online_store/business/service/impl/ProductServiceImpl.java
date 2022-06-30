package kz.tmq.tmq_online_store.business.service.impl;

import kz.tmq.tmq_online_store.auth.mapper.CommonMapper;
import kz.tmq.tmq_online_store.business.dto.product.*;
import kz.tmq.tmq_online_store.business.entity.Product;
import kz.tmq.tmq_online_store.business.repository.ProductRepository;
import kz.tmq.tmq_online_store.business.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CommonMapper commonMapper;

    @Override
    public ProductCreateResponse create(ProductCreateRequest createRequest) {
        Product creatingProduct = commonMapper.convertTo(createRequest, Product.class);
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
    public ProductUpdateResponse update(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product = repository.findById(id).get();
        Product updatingProduct = commonMapper.convertTo(productUpdateRequest, Product.class);
        product.setTitle(updatingProduct.getTitle());
        product.setKeywords(updatingProduct.getKeywords());
        product.setPrice(updatingProduct.getPrice());
        product.setDescription(updatingProduct.getDescription());
        product.setCategory(updatingProduct.getCategory());

        Product updatedProduct = repository.save(product);

        ProductUpdateResponse productUpdateResponse = commonMapper.convertTo(updatedProduct, ProductUpdateResponse.class);

        return productUpdateResponse;
    }
}
