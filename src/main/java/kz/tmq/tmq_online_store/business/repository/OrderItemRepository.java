package kz.tmq.tmq_online_store.business.repository;

import kz.tmq.tmq_online_store.business.entity.Order;
import kz.tmq.tmq_online_store.business.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
