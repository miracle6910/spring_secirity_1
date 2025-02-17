package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        boolean isAdmin = authentication.getAuthorities().stream()
                .map((authority) -> authority.getAuthority())
                .anyMatch((a) -> a.equals("ROLE_ADMIN"));
        boolean isUser = authentication.getAuthorities().stream()
                .map((authority) -> authority.getAuthority())
                .anyMatch((a) -> a.equals("ROLE_USER"));
        if (isAdmin) {
            httpServletResponse.sendRedirect("admin");
        } else if (isUser) {
            httpServletResponse.sendRedirect("user");
        } else {
            httpServletResponse.sendRedirect("login");
        }
    }
}