package com.mmrath.sapp.repository.security;

import com.mmrath.sapp.domain.security.Permission;
import com.mmrath.sapp.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(name = Role.QUERY_GET_UNASSIGNED_PERMISSIONS)
    List<Permission> findAllUnassignedPermissions(@Param("id") Long id);

}
