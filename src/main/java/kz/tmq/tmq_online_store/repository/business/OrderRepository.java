package kz.tmq.tmq_online_store.repository.business;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.domain.business.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findAllByUser(User user);

}
