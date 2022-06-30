package kz.tmq.tmq_online_store.business.service;

import kz.tmq.tmq_online_store.business.dto.product.*;
import kz.tmq.tmq_online_store.business.entity.Product;

import java.util.List;

public interface ProductService {
    public ProductCreateResponse create(ProductCreateRequest createRequest);

    public Product findOne(Long id);

    public ProductFindOneResponse findById(Long id);

    public List<ProductFindAllResponse> findAll();

    public void delete(Long id);

    public ProductUpdateResponse update(Long id, ProductUpdateRequest productUpdateRequest);
}
