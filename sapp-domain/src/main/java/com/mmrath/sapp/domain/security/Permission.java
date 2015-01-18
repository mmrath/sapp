package com.mmrath.sapp.domain.security;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Cacheable
@Table(name = "t_permission")
public class Permission implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  @Column(name = "name", unique = true, nullable = false,
      updatable = false, insertable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Override
  public String toString() {
    return "Permission{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Permission that = (Permission) o;

    if (id != null ? !id.equals(that.id) : that.id != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
