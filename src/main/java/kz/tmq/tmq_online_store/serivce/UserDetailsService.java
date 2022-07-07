package kz.tmq.tmq_online_store.serivce;

import kz.tmq.tmq_online_store.domain.UserDetails;

public interface UserDetailsService {

    UserDetails findById(Long id);



}
