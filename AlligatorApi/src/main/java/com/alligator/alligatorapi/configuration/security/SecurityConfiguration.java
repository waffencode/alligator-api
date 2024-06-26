package com.alligator.alligatorapi.configuration.security;

import com.alligator.alligatorapi.model.entity.enums.RoleName;
import com.alligator.alligatorapi.model.repository.user.RoleRepository;
import com.alligator.alligatorapi.service.JwtService;
import com.alligator.alligatorapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Admin has full access to project data. <br><br>
 * All users have Read-only access to any data. <br><br>
 * Business analytics have full project-backlog control. <br><br>
 * Rest endpoints have custom (mostly team-based) security logic, defined on repositories. <br><br>
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtService jwtService;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable);
        http
                .addFilterBefore(new JwtFilter(jwtService, userService), BasicAuthenticationFilter.class);

        http
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                                .requestMatchers("/test").authenticated()

                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/whoami").authenticated()

                                // All users have Read-only access
                                .requestMatchers(HttpMethod.GET, "/**").authenticated()

                                // Business analytics have full project-backlog control
                                .requestMatchers(
                                        "/tasks/**",
                                        "/deadlines/**",
                                        "/taskDependencies/**").hasAuthority(RoleName.BUSINESS_ANALYTIC.name())

                                // all other requests will be validated on endpoints (in repositories)
                                .requestMatchers("/**").authenticated()

                                /*
                                TODO: Ну.. Опять переписывать секьюр - надо дописать метод/интерфейс секьюр для всех репозиториев
                                 */
                );

        return http.build();
    }
}
