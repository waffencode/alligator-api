package com.alligator.alligatorapi.repository.sprint;

import com.alligator.alligatorapi.entity.sprint.Sprint;
import com.alligator.alligatorapi.entity.sprint.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
    @PreAuthorize("@securityService.validatePrincipalIsTeamLeadOfTeam(sprint.team)")
    <S extends Sprint> S save(S sprint);

    @RestResource(exported = false)
    <S extends Sprint> List<S> saveAll(Iterable<S> sprints);
}