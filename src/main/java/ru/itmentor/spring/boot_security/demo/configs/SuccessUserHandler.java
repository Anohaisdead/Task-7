package ru.itmentor.spring.boot_security.demo.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.contains("ROLE_USER")) {
            Object principal = authentication.getPrincipal();
            Long userId = null;

            if (principal instanceof User) {
                userId = ((User) principal).getId();
            }
            if (userId != null) {
                httpServletResponse.sendRedirect("/user/" + userId); // Перенаправление на URL с ID
            } else {
                httpServletResponse.sendRedirect("/login?error=missing_user_id");
            }
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}