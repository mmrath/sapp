package com.mmrath.sapp.spring.security;

import com.mmrath.sapp.domain.security.User;
import com.mmrath.sapp.util.JsonUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String sessionId=request.getSession().getId();
        response.setStatus(HttpServletResponse.SC_OK);
        Object o1 = authentication.getPrincipal();
        if(o1 instanceof User){
            response.getWriter().append(JsonUtils.asJsonString(o1));
        }
        clearAuthenticationAttributes(request);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
