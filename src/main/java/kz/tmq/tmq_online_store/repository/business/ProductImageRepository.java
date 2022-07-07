package kz.tmq.tmq_online_store.repository.business;

import kz.tmq.tmq_online_store.domain.business.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
