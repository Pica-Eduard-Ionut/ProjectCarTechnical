package com.example.CarTechnical.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();

            if (role.equals("ROLE_OWNER")) {
                response.sendRedirect("/owner/dashboard");
                return;
            } else if (role.equals("ROLE_MECHANIC")) {
                response.sendRedirect("/mechanic/dashboard");
                return;
            } else if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin/dashboard");
                return;
            }
        }

        response.sendRedirect("/login?error=unauthorized");
    }
}
