package kz.tmq.tmq_online_store.business.repository;

import kz.tmq.tmq_online_store.business.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
