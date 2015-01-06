package com.mmrath.sapp.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(@Param("permissionName") String name);
}
