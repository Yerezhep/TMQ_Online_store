package kz.tmq.tmq_online_store.business.repository;

import kz.tmq.tmq_online_store.business.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
