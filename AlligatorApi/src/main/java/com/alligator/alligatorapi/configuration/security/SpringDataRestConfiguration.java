package com.alligator.alligatorapi.configuration.security;

import com.alligator.alligatorapi.model.entity.sprint.*;
import com.alligator.alligatorapi.model.entity.task.Deadline;
import com.alligator.alligatorapi.model.entity.task.Task;
import com.alligator.alligatorapi.model.entity.task.TaskDependency;
import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.model.entity.team.TeamRole;
import com.alligator.alligatorapi.model.entity.user.Role;
import com.alligator.alligatorapi.model.entity.user.User;
import com.alligator.alligatorapi.model.entity.user.UserInfo;
import com.alligator.alligatorapi.model.entity.user.UserRole;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class SpringDataRestConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        cors.addMapping("/**")
                .allowedOrigins("*")  // Allow all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH")  // Allow all HTTP methods
                .allowedHeaders("*");  // Allow all headers

        config.exposeIdsFor(AssignedTask.class,
                Sprint.class,
                SprintTask.class,
                SprintTaskRole.class,
                TaskSwapRequest.class,

                Deadline.class,
                Task.class,
                TaskDependency.class,

                Team.class,
                TeamMember.class,
                TeamMemberRole.class,
                TeamRole.class,

                Role.class,
                User.class,
                UserInfo.class,
                UserRole.class);
    }
}
