package kz.tmq.tmq_online_store.business.service;

import kz.tmq.tmq_online_store.business.dto.product.*;
import kz.tmq.tmq_online_store.business.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public ProductCreateResponse create(List<MultipartFile> files, String createRequest);

    public Product findOne(Long id);

    public ProductFindOneResponse findById(Long id);

    public List<ProductFindAllResponse> findAll();

    public void delete(Long id);

    public ProductUpdateResponse update(Long id, String productUpdateRequest, List<MultipartFile> files);
}
