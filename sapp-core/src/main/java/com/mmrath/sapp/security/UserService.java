package com.mmrath.sapp.security;

import com.mmrath.sapp.util.PasswordUtils;
import com.mmrath.sapp.utils.PersistentUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserCredentialRepository userCredentialRepository;

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

    @Transactional(readOnly = true)
    public User findUserByUsername(String loginId) {
        User user = userRepository.findByUsername(loginId);
        PersistentUtils.initialize(user);
        return user;
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
    public UserCredential findUserCredential(Long userId) {
        UserCredential userCredential = userCredentialRepository.findOne(userId);
        PersistentUtils.initialize(userCredential);
        return userCredential;
    }

    public User register(User user) {
        if (user.getCredential() != null && StringUtils.hasText(user.getCredential().getPassword())) {
            UserCredential userCredential = user.getCredential();
            String salt = PasswordUtils.generateSalt();
            String password = userCredential.getPassword();
            userCredential.setSalt(salt);
            String encodedPassword = PasswordUtils.encodePassword(password, salt);
            userCredential.setPassword(encodedPassword);
            Date expiryDate = new Date(System.currentTimeMillis() + 365 * 24 * 60 * 100);
            userCredential.setExpiryDate(expiryDate);
            userCredential.setInvalidAttempts(3);
            userCredentialRepository.saveAndFlush(user.getCredential());
        }
        user = userRepository.save(user);
        PersistentUtils.initialize(user);
        return user;
    }
}
