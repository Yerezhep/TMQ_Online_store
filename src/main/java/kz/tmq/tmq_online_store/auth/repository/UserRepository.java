package kz.tmq.tmq_online_store.auth.repository;

import kz.tmq.tmq_online_store.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByActivationCode(String activationCode);

    Optional<User> findByPasswordResetCode(String passwordResetCode);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
