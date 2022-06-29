package kz.tmq.tmq_online_store.business.repository;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.business.dto.cart.AddToCartDto;
import kz.tmq.tmq_online_store.business.entity.Cart;
import kz.tmq.tmq_online_store.business.entity.CartItem;
import kz.tmq.tmq_online_store.business.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Set<CartItem> findAllByCart(Cart cart);

    Optional<CartItem> findByCart(Cart cart);

}
