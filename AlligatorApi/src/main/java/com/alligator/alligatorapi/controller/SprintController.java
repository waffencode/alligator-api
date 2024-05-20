package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.model.dto.SprintDto;
import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.sprint.SprintTask;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.repository.sprint.SprintRepository;
import com.alligator.alligatorapi.service.SprintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class SprintController
{
    private final SprintService sprintService;
    private final SprintRepository sprintRepository;

    @PostMapping(path = "/assign")
    @ResponseStatus(HttpStatus.OK)
    public String assignTasks(@RequestBody SprintDto sprintDto) {
        log.info("Assigning tasks to sprint ID {}: {}", sprintDto.getId(), sprintDto);

        if ((sprintDto.getId() == null) || sprintRepository.findById(sprintDto.getId()).isEmpty())
        {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Sprint not found");
        }

        Sprint sprintEntity = sprintRepository.findById(sprintDto.getId()).get();
        List<SprintTask> list = new ArrayList<>();

        try {
            list = sprintService.suggestTaskAssignation(sprintEntity);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }

        for (SprintTask sprintTask : list)
        {
            log.info("Task to assign: {} | {}", sprintTask.getTask().getHeadline(), sprintTask.getTask().getDescription());
        }

        return list.toString();
    }
}
