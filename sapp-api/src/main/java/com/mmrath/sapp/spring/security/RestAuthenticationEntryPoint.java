package com.mmrath.sapp.spring.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  public void commence(HttpServletRequest arg0, HttpServletResponse response,
      AuthenticationException arg2) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not authenticated");
  }
}