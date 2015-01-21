package com.mmrath.sapp.service.security;

import com.mmrath.sapp.domain.security.Permission;
import com.mmrath.sapp.repository.security.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionService {

  private final PermissionRepository permissionRepository;

  @Autowired
  public PermissionService(PermissionRepository permissionRepository) {
    this.permissionRepository  = permissionRepository;
  }

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
