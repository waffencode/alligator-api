package com.alligator.alligatorapi.service;

import com.alligator.alligatorapi.entity.sprint.AssignedTask;
import com.alligator.alligatorapi.entity.sprint.Sprint;
import com.alligator.alligatorapi.entity.sprint.SprintTask;
import com.alligator.alligatorapi.entity.sprint.SprintTaskRole;
import com.alligator.alligatorapi.entity.team.TeamMember;
import com.alligator.alligatorapi.entity.team.TeamMemberRole;
import com.alligator.alligatorapi.entity.team.TeamRole;
import com.alligator.alligatorapi.repository.sprint.SprintTaskRequiredRoleRepository;
import com.alligator.alligatorapi.repository.team.TeamMemberRoleRepository;
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
