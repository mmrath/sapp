package com.mmrath.sapp.spring.config;

import com.mmrath.sapp.domain.security.PermissionConstants;
import com.mmrath.sapp.service.security.UserService;
import com.mmrath.sapp.spring.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.RememberMeServices;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private Environment env;

  @Autowired
  private UserService userService;

  @Autowired
  private RememberMeServices rememberMeServices;

  @Bean
  public ApplicationAuthenticationProvider applicationAuthenticationProvider(){
    return new ApplicationAuthenticationProvider(userService);
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(applicationAuthenticationProvider());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers("/scripts/**/*.{js,html}")
        .antMatchers("/bower_components/**")
        .antMatchers("/i18n/**")
        .antMatchers("/assets/**")
        .antMatchers("/swagger-ui/**")
        .antMatchers("/test/**")
        .antMatchers("/console/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //formatter:off

    http
        //.addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)  // See https://github.com/jhipster/generator-jhipster/issues/965
        .exceptionHandling()
        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
    .and()
        .rememberMe()
        .rememberMeServices(rememberMeServices)
        .key(env.getProperty("jhipster.security.rememberme.key"))
    .and()
        .formLogin()
        .loginProcessingUrl("/api/authentication")
        .successHandler(new RestLoginSuccessHandler())
        .failureHandler(new RestLoginFailureHandler())
        .usernameParameter("j_username")
        .passwordParameter("j_password")
        .permitAll()
    .and()
        .logout()
        .logoutUrl("/api/logout")
        .logoutSuccessHandler(new RestLogoutSuccessHandler())
        .deleteCookies("JSESSIONID", "hazelcast.sessionId", "CSRF-TOKEN")
        .permitAll()
    .and()
        .csrf()
        .disable() // See https://github.com/jhipster/generator-jhipster/issues/965
        .headers()
        .frameOptions()
        .disable()
    .and()
        .authorizeRequests()
        .antMatchers("/api/register").permitAll()
        .antMatchers("/api/activate").permitAll()
        .antMatchers("/api/authenticate").permitAll()
        .antMatchers("/api/logs/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/api/**").authenticated()
        .antMatchers("/websocket/tracker").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/websocket/**").permitAll()
        .antMatchers("/metrics/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/health/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/trace/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/dump/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/shutdown/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/beans/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/configprops/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/info/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/autoconfig/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/env/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/trace/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/api-docs/**").hasAuthority(PermissionConstants.OPS_ADMIN)
        .antMatchers("/protected/**").authenticated();
    //formatter:on
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
  static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {

  }
}
