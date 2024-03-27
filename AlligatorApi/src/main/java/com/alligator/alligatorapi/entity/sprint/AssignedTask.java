package com.alligator.alligatorapi.entity.sprint;

import com.alligator.alligatorapi.entity.team.TeamMember;
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
@Table(name = "assigned_tasks")
public class AssignedTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private SprintTask task;
    @ManyToOne
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    private Timestamp assignationTime;
}
