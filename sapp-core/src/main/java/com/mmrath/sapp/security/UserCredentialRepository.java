package com.mmrath.sapp.security;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Murali on 11/18/13.
 */
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

}
