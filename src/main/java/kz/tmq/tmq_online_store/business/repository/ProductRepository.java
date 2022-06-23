package kz.tmq.tmq_online_store.business.repository;

import kz.tmq.tmq_online_store.business.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
