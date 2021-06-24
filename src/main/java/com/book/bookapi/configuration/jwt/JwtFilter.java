package com.book.bookapi.configuration.jwt;

import com.book.bookapi.exceptions.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = jwtProvider.resolveToken((HttpServletRequest) servletRequest);
        if (!((HttpServletRequest) servletRequest).getRequestURL().toString().endsWith("auth/refresh")) {
            try {
                if (token != null && jwtProvider.validateToken(token)) {
                    Authentication authentication = jwtProvider.getAuthentication(token);
                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (JwtAuthenticationException e) {
                SecurityContextHolder.clearContext();
                throw new JwtAuthenticationException("Access token is expired or invalid");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
