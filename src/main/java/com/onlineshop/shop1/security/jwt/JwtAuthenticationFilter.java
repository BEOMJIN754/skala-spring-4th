package com.onlineshop.shop1.security.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.onlineshop.shop1.domain.customer.entity.CustomerRole;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private static final String BEARER_PREFIX = "Bearer ";

        private final JwtTokenProvider jwtTokenProvider;

        @Override
        protected void doFilterInternal(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain) throws ServletException, IOException {

                System.out.println(
                                "JWT 필터 실행: " + request.getRequestURI());

                String accessToken = resolveToken(request);

                if (accessToken != null
                                && jwtTokenProvider.validateToken(accessToken)
                                && jwtTokenProvider.isAccessToken(accessToken)) {

                        setAuthentication(accessToken);
                }

                filterChain.doFilter(request, response);
        }

        private String resolveToken(HttpServletRequest request) {

                String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

                if (authorizationHeader == null
                                || !authorizationHeader.startsWith(BEARER_PREFIX)) {
                        return null;
                }

                return authorizationHeader.substring(
                                BEARER_PREFIX.length());
        }

        private void setAuthentication(String accessToken) {

                String customerId = jwtTokenProvider.getCustomerId(accessToken);

                CustomerRole role = jwtTokenProvider.getRole(accessToken);

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                                "ROLE_" + role.name());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                customerId,
                                null,
                                List.of(authority));

                SecurityContextHolder
                                .getContext()
                                .setAuthentication(authentication);
        }
}