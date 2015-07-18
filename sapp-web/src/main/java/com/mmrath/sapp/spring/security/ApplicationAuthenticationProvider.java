package com.mmrath.sapp.spring.security;

import com.mmrath.sapp.domain.security.Credential;
import com.mmrath.sapp.domain.security.User;
import com.mmrath.sapp.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.mmrath.sapp.util.PasswordUtils.isPasswordValid;

@Component
public class ApplicationAuthenticationProvider implements AuthenticationProvider {

  private final UserService userService;

  @Autowired
  public ApplicationAuthenticationProvider(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    Optional<User> optionalUser = userService.findUserByUsername(username);
    if (!optionalUser.isPresent()) {
      throw new BadCredentialsException("Username or password is not valid.");
    }
    User user = optionalUser.get();
    Credential userCredential = user.getCredential();
    if (!isPasswordValid(userCredential.getPassword(), password, userCredential.getSalt())) {
      throw new BadCredentialsException("Username or password is not valid.");
    }
    Collection<? extends GrantedAuthority> authorities = getAuthorities(user);
    return new UsernamePasswordAuthenticationToken(user, password, authorities);
  }

  private List<GrantedAuthority> getAuthorities(User user) {
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    userService.findAllPermissions(user.getId())
        .forEach(permission -> authorities.add(() -> permission.getName()));
    return authorities;
  }


  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
