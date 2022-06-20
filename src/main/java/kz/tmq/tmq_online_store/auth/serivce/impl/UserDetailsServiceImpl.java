package kz.tmq.tmq_online_store.auth.serivce.impl;

import kz.tmq.tmq_online_store.auth.domain.UserDetails;
import kz.tmq.tmq_online_store.auth.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.auth.repository.UserDetailsRepository;
import kz.tmq.tmq_online_store.auth.serivce.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails findById(Long id) {
        return userDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Details", "id", String.valueOf(id)));
    }
}
