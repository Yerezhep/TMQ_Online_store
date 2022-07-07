package kz.tmq.tmq_online_store.repository.business;

import kz.tmq.tmq_online_store.domain.business.Cart;
import kz.tmq.tmq_online_store.domain.business.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByCartOrderByAddedAtDesc(Cart cart);

}
