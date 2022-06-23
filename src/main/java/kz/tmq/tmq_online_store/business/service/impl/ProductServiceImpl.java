package kz.tmq.tmq_online_store.business.service.impl;

import kz.tmq.tmq_online_store.auth.mapper.CommonMapper;
import kz.tmq.tmq_online_store.business.dto.product.ProductCreateRequest;
import kz.tmq.tmq_online_store.business.dto.product.ProductCreateResponse;
import kz.tmq.tmq_online_store.business.entity.Product;
import kz.tmq.tmq_online_store.business.repository.ProductRepository;
import kz.tmq.tmq_online_store.business.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Product findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
