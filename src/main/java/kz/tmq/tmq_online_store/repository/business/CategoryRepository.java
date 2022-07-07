package kz.tmq.tmq_online_store.repository.business;

import kz.tmq.tmq_online_store.domain.business.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
