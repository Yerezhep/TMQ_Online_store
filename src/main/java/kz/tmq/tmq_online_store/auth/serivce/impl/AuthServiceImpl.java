package kz.tmq.tmq_online_store.auth.serivce.impl;

import kz.tmq.tmq_online_store.auth.domain.Role;
import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.domain.enums.RoleEnum;
import kz.tmq.tmq_online_store.auth.dto.register.RegisterRequest;
import kz.tmq.tmq_online_store.auth.dto.register.RegisterResponse;
import kz.tmq.tmq_online_store.auth.mapper.CommonMapper;
import kz.tmq.tmq_online_store.auth.repository.UserRepository;
import kz.tmq.tmq_online_store.auth.serivce.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CommonMapper commonMapper;

    public AuthServiceImpl(UserRepository userRepository, CommonMapper commonMapper) {
        this.userRepository = userRepository;
        this.commonMapper = commonMapper;
    }

    @Override
    public RegisterResponse addUser(RegisterRequest registerRequest, BindingResult bindingResult) {
        User registeringUser = commonMapper.convertTo(registerRequest, User.class);
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(RoleEnum.ROLE_USER.name()));
        registeringUser.setRoles(roles);
        User registeredUser = userRepository.save(registeringUser);
        RegisterResponse registerResponse = commonMapper.convertTo(registeredUser, RegisterResponse.class);
        return registerResponse;
    }
}
