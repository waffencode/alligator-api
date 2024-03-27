package com.alligator.alligatorapi.configuration.repository;

import com.alligator.alligatorapi.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepositoriesConfiguration {
    private final SecurityService securityService;

//    @Bean
//    TeamRepositoriesEventsHandler teamRepositoriesEventsHandler() {
//        return new TeamRepositoriesEventsHandler(securityService);
//    }
}
