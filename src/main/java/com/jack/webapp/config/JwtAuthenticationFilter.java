package com.jack.webapp.config;

import com.jack.webapp.repositories.TokenRepository;
import com.jack.webapp.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Log
@Component
@ComponentScan("com.jack.webapp.services")
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    /*
     * FilterChain is used to invoke the next filter in the chain, or if the calling filter is the last filter in the chain, to invoke the resource at the end of the chain.
     * This method is called by the container each time a request is received.
     * */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if(request.getServletPath().contains("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeaader = request.getHeader("Authorization");
        if(authHeaader == null || !authHeaader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = request.getHeader("Authorization").substring(7);
        final String email = jwtService.extractUsername(jwt);

        if(email != null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            var isTokenValid = tokenRepository.findByToken(jwt).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
            if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
