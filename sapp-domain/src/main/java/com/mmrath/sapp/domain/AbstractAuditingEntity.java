package com.mmrath.sapp.domain;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity<T extends Serializable> implements Serializable {

  private static final long serialVersionUID = 1L;

  @CreatedBy
  @NotNull
  @Column(name = "created_by", nullable = false, length = 50, updatable = false)
  private String createdBy;

  @CreatedDate
  @NotNull
  @Column(name = "created_date", nullable = false)
  private Instant createdDate = Instant.now();

  @LastModifiedBy
  @Column(name = "last_modified_by", length = 50)
  private String lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private Instant lastModifiedDate = Instant.now();

  @Version
  private Integer version;

  public abstract T getId();

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Instant getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Instant lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }
}
