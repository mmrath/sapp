package com.mmrath.sapp.config;

import com.mmrath.sapp.Constants;
import com.mmrath.sapp.utils.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Created by murali on 19/01/15.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  public String getCurrentAuditor() {
    String userName = SecurityUtils.getCurrentLogin();
    return (userName != null ? userName : Constants.SYSTEM_ACCOUNT);
  }
}