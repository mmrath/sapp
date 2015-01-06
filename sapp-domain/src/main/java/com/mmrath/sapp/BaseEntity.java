package com.mmrath.sapp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;
    @JsonIgnore
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", nullable = false)
    private Date updateTime;
    @JsonIgnore
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;
    @Version
    private Integer version;

    @PrePersist
    protected void onCreate() {
        updateTime = createTime = new Date();
        createdBy = updatedBy = getCurrentUser();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = new Date();
        updatedBy = getCurrentUser();
    }

    private String getCurrentUser() {
        Object principal = "ReplaceMe";//SecurityUtils.getSubject().getPrincipal();
        if (principal != null) {
            return principal.toString();
        } else {
            return "unknown";
        }
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createTime=" + createTime +
                ", createdBy='" + createdBy + '\'' +
                ", updateTime=" + updateTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", version=" + version +
                '}';
    }

    public abstract Serializable getId();

    public final Date getCreateTime() {
        return createTime;
    }

    public final void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public final String getCreatedBy() {
        return createdBy;
    }

    public final void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public final Date getUpdateTime() {
        return updateTime;
    }

    public final void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public final String getUpdatedBy() {
        return updatedBy;
    }

    public final void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public final Integer getVersion() {
        return version;
    }

    public final void setVersion(Integer version) {
        this.version = version;
    }
}
