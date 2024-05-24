package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.model.repository.sprint.*;
import com.alligator.alligatorapi.model.repository.team.*;
import com.alligator.alligatorapi.model.repository.task.*;
import com.alligator.alligatorapi.model.repository.user.*;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Dependency injection container for all repositories.
 */
@Component
public abstract class RepositoryDependentService
{
    @Inject
    protected AssignedTaskRepository assignedTaskRepository;
    @Inject
    protected SprintRepository sprintRepository;
    @Inject
    protected SprintTaskRepository sprintTaskRepository;
    @Inject
    protected SprintTaskRequiredRoleRepository sprintTaskRequiredRoleRepository;

    @Inject
    protected DeadlineRepository deadlineRepository;
    @Inject
    protected TaskDependencyRepository taskDependencyRepository;
    @Inject
    protected TaskRepository taskRepository;

    @Inject
    protected TeamMemberRepository teamMemberRepository;
    @Inject
    protected TeamMemberRoleRepository teamMemberRoleRepository;
    @Inject
    protected TeamRepository teamRepository;
    @Inject
    protected TeamRoleRepository teamRoleRepository;

    @Inject
    protected RoleRepository roleRepository;
    @Inject
    protected UserInfoRepository userInfoRepository;
    @Inject
    protected UserRepository userRepository;
    @Inject
    protected UserRoleRepository userRoleRepository;
}