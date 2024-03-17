package com.alligator.alligatorapi.configuration.security;

import com.alligator.alligatorapi.service.JwtService;
import com.alligator.alligatorapi.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        Logger logger = Logger.getLogger(JwtFilter.class.getName());

        String requestUri = request.getRequestURI();

        if(requestUri.equals("/login") || requestUri.equals("/register")) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);

            // Perform JWT validation logic here
            if (jwtService.isValid(jwt)) {

                String username = jwtService.pareseUsername(jwt);
                Long userId = userService.loadFromDatabase(username).getId();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        "[PROTECTED]",
                        new ArrayList<>() // FILLER
                );

                Map<String, Object> details = new HashMap<>();
                details.put("id", userId);
                authentication.setDetails(details);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                logger.log(Level.INFO, "User " + username + " request to " + requestUri + " authenticated successfully.");

                chain.doFilter(request, response);
                return;
            }
        }

        logger.log(Level.INFO, "Got invalid jwt in request.");
        chain.doFilter(request, response);
    }
}