package com.alligator.alligatorapi.model.entity.sprint;

import com.alligator.alligatorapi.model.entity.enums.SprintState;
import com.alligator.alligatorapi.model.entity.team.Team;
import com.alligator.alligatorapi.model.entity.team.TeamMember;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "sprints")
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_id")
    @NotNull(message = "Team is required")
    private Team team;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "scrum_master_id")
    @NotNull(message = "Scrum master is required")
    private TeamMember scrumMaster;

    private Timestamp startTime;
    private Timestamp endTime;
    private Long sp;

    @NotNull(message = "Name is required")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "State is required")
    private SprintState state;
}
