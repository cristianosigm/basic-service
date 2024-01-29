package com.cs.sigm.util;

import com.cs.sigm.exception.CSAuthenticationException;
import com.cs.sigm.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private com.cs.sigm.service.CSUserDetailsService CSUserDetailsService;

    @Autowired
    private TokenBlacklistService blacklistService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CSUserSettings settings;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        final String token = jwtService.extractToken(request);
        if (StringUtils.hasText(token)) {
            if (blacklistService.isBlacklisted(token)) {
                log.warn("Tried to log in with a blacklisted token!");
                throw new CSAuthenticationException(settings.getMessage("exception.expired"));
            } else {
                username = jwtService.extractUsername(token);
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = CSUserDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
