package diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.securityfilters;


import diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger.securityauthentication.MySecurityAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class MySecurityFilter extends OncePerRequestFilter {

    private final String HEADER_LOGIN = "x-myfilter-login";
    private final String HEADER_PASSWORD = "x-myfilter-password";

    private final AuthenticationManager authenticationManager;

    public MySecurityFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("Before filterchain");

        if (!Collections.list(request.getHeaderNames()).contains(HEADER_LOGIN) || !Collections.list(request.getHeaderNames()).contains(HEADER_PASSWORD)) {
            filterChain.doFilter(request, response);
            return;
        }
        var login = request.getHeader(HEADER_LOGIN);
        var password = request.getHeader(HEADER_PASSWORD);
        var authRequest = MySecurityAuthentication.unauthenticated(login, password);
        try {
            var authentication =  authenticationManager.authenticate(authRequest);
            var newContext = SecurityContextHolder.createEmptyContext();
            newContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(newContext);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().println("Invalid credentials!");
        }

        System.out.println("After filterchain\n");
    }

}
