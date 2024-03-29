package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.entity.sprint.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class SprintService {


    public List<AssignedTask> suggestTaskAssignation(Sprint sprint) {
        Logger.getLogger(SprintService.class.getName()).info("This doesn't works yet...");
        return new ArrayList<>();
    }
}
