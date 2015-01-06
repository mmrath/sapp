package com.mmrath.sapp.security;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Cacheable
@Table(name = "t_permission",
        uniqueConstraints = @UniqueConstraint(columnNames = {"module_id", "access_level_id"}))
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "module_id", unique = false, nullable = false, updatable = false, insertable = false)
    private int moduleId;

    @Column(name = "access_level_id", unique = false, nullable = false, updatable = false, insertable = false)
    private int accessLevelId;

    @Column(name = "name", unique = true, nullable = false, updatable = false, insertable = false)
    private String name;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", moduleId=" + moduleId +
                ", accessLevelId=" + accessLevelId +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }

    public Long getId() {
        return id;
    }

    public AccessLevel getAccessLevel() {
        return AccessLevel.getKey(this.accessLevelId);
    }

    public Module getModule() {
        return Module.getKey(this.moduleId);
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevelId = accessLevel.getValue();
    }

    public void setModule(Module module) {
        this.moduleId = module.getValue();
    }

    public String getName() {
        return name;
    }
}
