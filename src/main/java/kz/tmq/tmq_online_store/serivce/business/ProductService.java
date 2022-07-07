package kz.tmq.tmq_online_store.serivce.business;

import kz.tmq.tmq_online_store.dto.product.ProductCreateResponse;
import kz.tmq.tmq_online_store.dto.product.ProductFindAllResponse;
import kz.tmq.tmq_online_store.dto.product.ProductFindOneResponse;
import kz.tmq.tmq_online_store.dto.product.ProductUpdateResponse;
import kz.tmq.tmq_online_store.domain.business.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    ProductCreateResponse create(List<MultipartFile> files, String createRequest);

    void delete(Long id);

    ProductUpdateResponse update(Long id, String productUpdateRequest, List<MultipartFile> files);
}
