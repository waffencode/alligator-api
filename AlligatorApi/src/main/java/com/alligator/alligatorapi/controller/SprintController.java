package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.service.JwtService;
import com.alligator.alligatorapi.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SprintController
{
    private final SprintService sprintService;

    @PostMapping(path = "/assign")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> assignTasks(@RequestBody Sprint sprint) {
        sprintService.suggestTaskAssignation(sprint);

        return ResponseEntity.ok().build();
    }
}
