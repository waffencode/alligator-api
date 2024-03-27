package com.alligator.alligatorapi.entity.sprint;

import com.alligator.alligatorapi.entity.enums.SprintState;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "scrum_master_id")
    private TeamMember scrumMaster;

    private Timestamp startTime;
    private Timestamp endTime;
    private Long sp;

    @Enumerated(EnumType.STRING)
    private SprintState state;
}
