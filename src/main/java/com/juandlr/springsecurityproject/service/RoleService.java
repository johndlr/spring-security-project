package com.juandlr.springsecurityproject.service;

import com.juandlr.springsecurityproject.entity.Role;

public interface RoleService {

    Role generateDefaultRoleName(String roleName);

}
