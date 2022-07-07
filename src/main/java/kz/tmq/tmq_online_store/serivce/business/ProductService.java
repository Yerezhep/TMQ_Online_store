package kz.tmq.tmq_online_store.serivce.business;

import kz.tmq.tmq_online_store.dto.product.ProductCreateResponse;
import kz.tmq.tmq_online_store.dto.product.ProductFindAllResponse;
import kz.tmq.tmq_online_store.dto.product.ProductFindOneResponse;
import kz.tmq.tmq_online_store.dto.product.ProductUpdateResponse;
import kz.tmq.tmq_online_store.domain.business.Product;
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
