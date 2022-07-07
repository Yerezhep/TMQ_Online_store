package kz.tmq.tmq_online_store.repository.business;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.domain.business.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

    boolean existsByUser(User user);

}
