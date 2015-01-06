package com.mmrath.sapp.security;

import org.springframework.data.domain.Sort;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class PermissionService {

    @Inject
    private PermissionRepository permissionRepository;

    public List<Permission> findAllPermissions() {
        return permissionRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    public Permission findPermissionById(Long id) {
        return permissionRepository.findOne(id);
    }

    public Permission findPermissionByName(String permissionName) {
        return permissionRepository.findByName(permissionName);
    }
}
