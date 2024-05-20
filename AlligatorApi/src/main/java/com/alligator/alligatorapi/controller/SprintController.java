package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.model.repository.sprint.SprintRepository;
import com.alligator.alligatorapi.service.SprintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String assignTasks(@RequestBody Sprint sprint) {
        log.info("Assigning tasks to sprint {}", sprint);
        List<TeamMember> list = new ArrayList<>();

        // TODO: Fix exception throwing with valid sprint.
        if ((sprint.getId() == null) || sprintRepository.findById(sprint.getId()).isEmpty())
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Sprint not found");

        try {
            list = sprintService.suggestTaskAssignation(sprint);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }

        return list.toString();
    }
}
