package com.mmrath.sapp.service.security;

import com.mmrath.sapp.domain.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by murali on 20/01/15.
 */
@Component("userDetailsService")
public class SpringSecurityUserDetailsService implements UserDetailsService {

  private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);


  private UserService userService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String login) {
    log.debug("Authenticating {}", login);
    String lowercaseLogin = login.toLowerCase();
    Optional<User> userFromDatabase = userService.findUserByUsername(lowercaseLogin);
    return userFromDatabase.map(user -> {
      /*if (!user.getActivated()) {
        throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
      }*/
      List<GrantedAuthority> grantedAuthorities =
          userService.findAllPermissions(user.getId()).stream()
              .map(permission -> new SimpleGrantedAuthority(permission.getName()))
              .collect(Collectors.toList());

      return new org.springframework.security.core.userdetails.User(lowercaseLogin, null,
          grantedAuthorities);
    }).orElseThrow(() -> new UsernameNotFoundException(
        "User " + lowercaseLogin + " was not found in the database"));
  }
}
