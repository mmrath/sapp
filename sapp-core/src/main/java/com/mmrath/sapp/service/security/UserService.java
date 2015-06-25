package com.mmrath.sapp.service.security;

import com.mmrath.sapp.domain.security.Credential;
import com.mmrath.sapp.domain.security.Permission;
import com.mmrath.sapp.domain.security.Role;
import com.mmrath.sapp.domain.security.User;
import com.mmrath.sapp.repository.security.UserCredentialRepository;
import com.mmrath.sapp.repository.security.UserRepository;
import com.mmrath.sapp.util.PasswordUtils;
import com.mmrath.sapp.utils.PersistentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

  private final UserRepository userRepository;
  private final UserCredentialRepository userCredentialRepository;

  @Autowired
  public UserService(UserRepository userRepository,
      UserCredentialRepository userCredentialRepository) {
    this.userRepository = userRepository;
    this.userCredentialRepository = userCredentialRepository;
  }

  @Transactional
  public User createUser(User user) {
    Assert.isTrue(user.getId() == null || user.getId() == 0L,
        "Invalid argument. User id must not be set");
    user = userRepository.save(user);
    return user;
  }

  @Transactional
  public User updateUser(User user) {
    Assert.isTrue(user.getId() != null && user.getId() != 0L,
        "Invalid argument. User id must be set");
    user = userRepository.save(user);
    return user;
  }

  @Transactional
  public Long deleteUser(Long userId) {
    userRepository.delete(userId);
    return userId;
  }

  /**
   * This will not return associated data
   *
   * @param userId
   * @return
   */
  @Transactional
  public User findUser(Long userId) {
    User user = userRepository.findOne(userId);
    PersistentUtils.initialize(user);
    return user;
  }

  @Transactional
  public Page<User> findUsers(Optional<Specification<User>> spec, Pageable pageable){
    if(spec.isPresent()){
      return userRepository.findAll(spec.get(),pageable);
    }else {
      return userRepository.findAll(pageable);
    }
  }

  @Transactional(readOnly = true)
  public Optional<User> findUserByUsername(String loginId) {
    Optional<User> optionalUser = userRepository.findOneByUsername(loginId);
    PersistentUtils.initialize(optionalUser);
    return optionalUser;
  }


  @Transactional(readOnly = true)
  public List<Role> findUserRoles(Long userId) {
    List<Role> roles = userRepository.findOne(userId).getRoles();
    PersistentUtils.initialize(roles);
    return roles;
  }


  @Transactional(readOnly = true)
  public List<Permission> findAllPermissions(Long userId) {
    List<Permission> userPermissions = new ArrayList<>();
    User user = userRepository.getOne(userId);
    if (user == null) {
      return userPermissions;
    }
    for (Role role : user.getRoles()) {
      userPermissions.addAll(role.getPermissions());
    }
    return userPermissions;
  }

  @Transactional(readOnly = true)
  public Credential findUserCredential(Long userId) {
    Credential credential = userCredentialRepository.findOne(userId);
    PersistentUtils.initialize(credential);
    return credential;
  }

  public User register(User user) {
    if (user.getCredential() != null && StringUtils.hasText(user.getCredential().getPassword())) {
      Credential credential = user.getCredential();
      String salt = PasswordUtils.generateSalt();
      String password = credential.getPassword();
      credential.setSalt(salt);
      String encodedPassword = PasswordUtils.encodePassword(password, salt);
      credential.setPassword(encodedPassword);
      Date expiryDate = new Date(System.currentTimeMillis() + 365 * 24 * 60 * 100);
      credential.setExpiryDate(expiryDate.toInstant());
      credential.setInvalidAttempts(3);
      userCredentialRepository.saveAndFlush(user.getCredential());
    }
    user = userRepository.save(user);
    PersistentUtils.initialize(user);
    return user;
  }
}
