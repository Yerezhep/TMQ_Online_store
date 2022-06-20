package kz.tmq.tmq_online_store.auth.serivce;

import kz.tmq.tmq_online_store.auth.domain.UserDetails;

public interface UserDetailsService {

    UserDetails findById(Long id);

}
