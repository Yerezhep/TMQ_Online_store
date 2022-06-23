package kz.tmq.tmq_online_store.business.service;

import kz.tmq.tmq_online_store.business.dto.product.ProductCreateRequest;
import kz.tmq.tmq_online_store.business.dto.product.ProductCreateResponse;
import kz.tmq.tmq_online_store.business.entity.Product;

import java.util.List;

public interface ProductService {
    public ProductCreateResponse create(ProductCreateRequest createRequest);


    public Product findById(Long id);

    public List<Product> findAll();

    void delete(Long id);
}
