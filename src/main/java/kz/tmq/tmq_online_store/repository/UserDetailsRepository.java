package kz.tmq.tmq_online_store.repository;

import kz.tmq.tmq_online_store.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {



}
