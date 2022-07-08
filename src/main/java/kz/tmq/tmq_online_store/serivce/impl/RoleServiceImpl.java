package kz.tmq.tmq_online_store.serivce.impl;

import kz.tmq.tmq_online_store.domain.Role;
import kz.tmq.tmq_online_store.domain.enums.RoleEnum;
import kz.tmq.tmq_online_store.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.repository.RoleRepository;
import kz.tmq.tmq_online_store.serivce.RoleService;
import kz.tmq.tmq_online_store.constant.AuthConstant;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(AuthConstant.ROLE, AuthConstant.NAME, name));
    }
}
