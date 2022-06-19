package kz.tmq.tmq_online_store.auth.serivce.impl;

import kz.tmq.tmq_online_store.auth.domain.Role;
import kz.tmq.tmq_online_store.auth.domain.enums.RoleEnum;
import kz.tmq.tmq_online_store.auth.exception.auth.ResourceNotFoundException;
import kz.tmq.tmq_online_store.auth.repository.RoleRepository;
import kz.tmq.tmq_online_store.auth.serivce.RoleService;
import org.springframework.stereotype.Service;

import static kz.tmq.tmq_online_store.auth.constant.AuthConstant.*;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(RoleEnum.ROLE_USER.name())
                .orElseThrow(() -> new ResourceNotFoundException(ROLE, NAME, RoleEnum.ROLE_USER.name()));
    }
}
