package kz.tmq.tmq_online_store.serivce.impl;

import kz.tmq.tmq_online_store.domain.UserDetails;
import kz.tmq.tmq_online_store.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.repository.UserDetailsRepository;
import kz.tmq.tmq_online_store.serivce.UserDetailsService;
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
