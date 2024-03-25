package com.alligator.alligatorapi.configuration.security;

import com.alligator.alligatorapi.service.JwtService;
import com.alligator.alligatorapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtService jwtService;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable());

        http
                .cors(cors -> cors.disable());

        http
                .addFilterBefore(new JwtFilter(jwtService, userService), BasicAuthenticationFilter.class);

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()

                        .requestMatchers(
                                "/tasks/**",
                                "/deadlines/**",
                                "/taskDependencies/**").hasAuthority("ROLE_BUSINESS_ANALYTIC")
                        .requestMatchers(HttpMethod.GET,
                                "/tasks/**",
                                "/deadlines/**",
                                "/taskDependencies/**").authenticated()
                );

        return http.build();
    }
}
