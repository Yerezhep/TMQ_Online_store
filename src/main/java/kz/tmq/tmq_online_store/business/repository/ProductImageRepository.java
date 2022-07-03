package kz.tmq.tmq_online_store.business.repository;

import kz.tmq.tmq_online_store.business.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
