package com.mmrath.sapp.spring.config;

import com.mmrath.sapp.service.security.UserService;
import com.mmrath.sapp.spring.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserService userService;

  @Bean
  public ApplicationAuthenticationProvider applicationAuthenticationProvider(){
    return new ApplicationAuthenticationProvider(userService);
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(applicationAuthenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //formatter:off
    http.csrf().disable();

    http.exceptionHandling()
        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
        .and().csrf().disable()
        .formLogin()
        .loginProcessingUrl("/api/login")
        .passwordParameter("password")
        .usernameParameter("username")
        .successHandler(new RestLoginSuccessHandler())
        .failureHandler(new RestLoginFailureHandler())
        .and()
        .logout()
        .logoutUrl("/api/logout")
        .logoutSuccessHandler(new RestLogoutSuccessHandler())
        .invalidateHttpSession(true)
        .and()
        .authorizeRequests()
        .antMatchers("/api/user/register").anonymous()
        .antMatchers("/**").anonymous()
        .antMatchers("/api/**").authenticated();

    //formatter:on
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
  private static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {

  }
}
