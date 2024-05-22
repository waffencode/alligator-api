package com.alligator.alligatorapi.controller;

import com.alligator.alligatorapi.model.deserializer.SprintDeserializer;
import com.alligator.alligatorapi.model.dto.EntityHrefLink;
import com.alligator.alligatorapi.model.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.model.entity.sprint.Sprint;
import com.alligator.alligatorapi.service.SprintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class SprintController
{
    private final SprintService sprintService;
    private final SprintDeserializer sprintDeserializer;

    /**
     * POST endpoint to assign tasks to a sprint.
     *
     * @param sprintLink the link to the sprint to which tasks will be assigned, deserialized to a Sprint object.
     * @return a list of AssignedTask objects that were created and assigned to the sprint.
     *
     * Responds with HTTP 201 Created status.
     */
    @PostMapping(path = "/assign")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AssignedTask> doAssignation(@RequestBody EntityHrefLink sprintLink) {
        Sprint sprint = sprintDeserializer.deserialize(sprintLink);

        return sprintService.doTaskAssignation(sprint);
    }
}
