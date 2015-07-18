package com.mmrath.sapp.repository.security;

import com.mmrath.sapp.domain.security.AccessLevel;
import com.mmrath.sapp.domain.security.Permission;
import com.mmrath.sapp.domain.security.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("select distinct p.resource from Permission p")
    List<Resource> findDistinctResources();

    @Query("select p.accessLevel from Permission p where p.resource =:resource")
    List<AccessLevel> findAccessLevelsForResource(Resource resource);
}
