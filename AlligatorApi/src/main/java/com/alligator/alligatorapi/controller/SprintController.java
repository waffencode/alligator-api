package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import com.alligator.alligatorapi.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SprintController
{
    private final SprintService sprintService;

    @PostMapping(path = "/assign")
    @ResponseStatus(HttpStatus.OK)
    public String assignTasks(@RequestBody Sprint sprint) {
        List<TeamMember> list = sprintService.suggestTaskAssignation(sprint);

        return list.toString();
    }
}
