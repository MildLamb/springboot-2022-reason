package com.mildlamb.service.impl;

import com.mildlamb.service.RoleService;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Override
    public void check() {
        System.out.println("role service 1 ... ");
    }
}
