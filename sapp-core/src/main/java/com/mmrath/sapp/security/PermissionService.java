package com.mmrath.sapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionService {

  private final PermissionRepository permissionRepository;

  @Autowired
  public PermissionService(PermissionRepository permissionRepository) {
    this.permissionRepository  = permissionRepository;
  }

  @Secured("USER_VIEW")
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
