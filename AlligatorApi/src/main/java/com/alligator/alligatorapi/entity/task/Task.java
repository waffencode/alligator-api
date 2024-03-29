package com.alligator.alligatorapi.entity.task;

import com.alligator.alligatorapi.entity.enums.TaskPriority;
import com.alligator.alligatorapi.entity.enums.TaskState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deadline_id")
    private Deadline deadline;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    private TaskState state;

    private String headline;
    private String description;

}
