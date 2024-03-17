package com.alligator.alligatorapi.entity;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private Timestamp startTime;
    private Timestamp endTime;
    private Long sp;
}
