package com.alligator.alligatorapi.configuration.repository;

import com.alligator.alligatorapi.entity.enums.RoleNames;
import com.alligator.alligatorapi.entity.sprint.Team;
import com.alligator.alligatorapi.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class TeamRepositoriesEventsHandler {
    private final SecurityService securityService;

    @HandleBeforeCreate
    @HandleBeforeSave
    public void handleTeamCreate(Team team) throws AccessDeniedException {
        Logger.getLogger(TeamRepositoriesEventsHandler.class.getName())
                .info("CREATE and SAVE: User with role ADMIN? "+ securityService.hasRole(RoleNames.ADMIN));
    }

    //TODO: Обновить модель - продумать как будет производиться работа с удалением команд и других сущностей
    // 1 (?):   разрешить удаление всем кто может создавать сущности, но при этом не добавить различные состояния
    //          акутальности данных. Пример: команда устарела, член команды вышел из команды... тд
}
