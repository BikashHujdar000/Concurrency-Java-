package com.example.bikash.SpringTest.JWT;

import com.example.bikash.SpringTest.Exceptions.JwtValidationException;
import com.example.bikash.SpringTest.Securities.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final  JwtService jwtService;

    private final CustomUserDetailsService userServices;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userServices) {
        this.jwtService = jwtService;
        this.userServices = userServices;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.substring(7);

            try {
                if (jwtService.validateToken(token)) {
                    String username = this.jwtService.getUsernameFromToken(token);
                    UserDetails userDetails = userServices.loadUserByUsername(username);

                    if(SecurityContextHolder.getContext().getAuthentication() == null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (JwtValidationException e) {
                request.setAttribute("jwtValidationException", e);
            }

        }
        filterChain.doFilter(request, response);
    }
}
