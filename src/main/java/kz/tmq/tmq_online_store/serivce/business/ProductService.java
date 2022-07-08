package kz.tmq.tmq_online_store.serivce.business;

import kz.tmq.tmq_online_store.dto.product.ProductResponse;
import kz.tmq.tmq_online_store.domain.business.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    ProductResponse add(List<MultipartFile> files, String createRequest);

    void delete(Long id);

    ProductResponse update(Long id, String productUpdateRequest, List<MultipartFile> files);

}
