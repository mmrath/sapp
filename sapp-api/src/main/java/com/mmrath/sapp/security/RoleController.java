package com.mmrath.sapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

  private final RoleService roleService;

  @Autowired
  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }


  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public Page<Role> findRoles(Pageable pageRequest) {
    return roleService.findAllRoles(pageRequest);
  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) {
    role = roleService.createRole(role);
    return new ResponseEntity<>(role, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public ResponseEntity<Role> updateRole(@PathVariable("id") Long id,
      @Valid @RequestBody Role role) {
    role = roleService.updateRole(role);
    return new ResponseEntity<>(role, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<Role> findRole(@PathVariable("id") Long id) {
    Role role = roleService.findRoleById(id);
    if (role == null) {
      return new ResponseEntity<>(role, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(role, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}/permissions", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<List<Permission>> findAssignedPermissions(@PathVariable("id") Long id) {
    List<Permission> permissions = roleService.findRolePermissions(id);
    return new ResponseEntity<>(permissions, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}/permissions/unassigned", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<List<Permission>> findUnassignedPermissions(@PathVariable("id") Long id) {
    List<Permission> permissions = roleService.findPermissionsUnassignedToRole(id);
    return new ResponseEntity<>(permissions, HttpStatus.OK);
  }
}
