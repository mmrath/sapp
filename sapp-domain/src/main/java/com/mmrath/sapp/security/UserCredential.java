package com.mmrath.sapp.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_user_credential")
public class UserCredential implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @OneToOne(cascade = {CascadeType.REFRESH})
    @MapsId
    public User user;

    @JsonIgnore
    private String salt;

    @JsonIgnore
    private String password;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @Column(name = "invalid_attempts", nullable = false)
    private int invalidAttempts;

    @Column(name = "locked", nullable = false)
    private Boolean locked;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getInvalidAttempts() {
        return invalidAttempts;
    }

    public void setInvalidAttempts(int invalidAttempts) {
        this.invalidAttempts = invalidAttempts;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "UserCredential{" +
                "user=" + user +
                ", expiryDate=" + expiryDate +
                ", invalidAttempts=" + invalidAttempts +
                ", locked=" + locked +
                '}';
    }
}
