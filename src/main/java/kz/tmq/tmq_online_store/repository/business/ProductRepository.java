package kz.tmq.tmq_online_store.repository.business;

import kz.tmq.tmq_online_store.domain.business.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
