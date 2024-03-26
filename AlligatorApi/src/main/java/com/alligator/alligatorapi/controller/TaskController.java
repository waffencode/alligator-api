package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

}
