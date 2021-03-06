package com.mmrath.sapp.api.security;

import com.mmrath.sapp.domain.security.Permission;
import com.mmrath.sapp.service.security.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security/permissions")
public class PermissionController {

  private final PermissionService permissionService;

  @Autowired
  public PermissionController(PermissionService permissionService){
    this.permissionService = permissionService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public List<Permission> getPermissions() {
    return permissionService.findAllPermissions();
  }
}
