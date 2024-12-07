package com.juandlr.springsecurityproject.service.implementation;

import com.juandlr.springsecurityproject.entity.Role;
import com.juandlr.springsecurityproject.exception.RoleNameNotFoundException;
import com.juandlr.springsecurityproject.repository.RoleRepository;
import com.juandlr.springsecurityproject.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role generateDefaultRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RoleNameNotFoundException("Role with the given name does not exists"));

    }
}
