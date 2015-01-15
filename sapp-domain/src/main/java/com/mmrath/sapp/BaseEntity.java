package com.mmrath.sapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmrath.sapp.security.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity<T extends Serializable> implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false, updatable = false)
  private Date createdDate;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false, updatable = false)
  private User createdBy;

  @JsonIgnore
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "last_modified_date", nullable = false)
  private Date lastModifiedDate;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "last_modified_by", nullable = false)
  private User lastModifiedBy;

  @Version
  private Integer version;

  @PrePersist
  protected void onCreate() {
    lastModifiedDate = createdDate = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    lastModifiedDate = new Date();
  }

  public abstract T getId();

  public boolean isNew() {
    return null == this.getId();
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public User getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(User lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }
}
