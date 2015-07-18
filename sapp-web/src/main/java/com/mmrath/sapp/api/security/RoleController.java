package com.mmrath.sapp.api.security;

import com.mmrath.sapp.ResourceNotFoundException;
import com.mmrath.sapp.domain.security.Permission;
import com.mmrath.sapp.domain.security.Role;
import com.mmrath.sapp.service.security.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public Role createRole(@Valid @RequestBody Role role) {
    role = roleService.createRole(role);
    return role;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public Role updateRole(@PathVariable("id") Long id,
      @Valid @RequestBody Role role) {
    role = roleService.updateRole(role);
    return role;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Role findRole(@PathVariable("id") Long id) {
    Role role = roleService.findRoleById(id);
    if (role == null) {
      throw new ResourceNotFoundException("Role not found");
    }
    return role;
  }

  @RequestMapping(value = "/{id}/permissions", method = RequestMethod.GET)
  @ResponseBody
  public List<Permission> findAssignedPermissions(@PathVariable("id") final Long id,
      @RequestParam(defaultValue = "false") final boolean unassigned) {
    if(!unassigned) {
      return roleService.findRolePermissions(id);
    }else{
      return roleService.findPermissionsUnassignedToRole(id);
    }
  }

}
