package com.alligator.alligatorapi.model.entity.sprint;

import com.alligator.alligatorapi.model.entity.task.Task;
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
@Table(name = "sprint_tasks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sprint_id", "task_id"})
})
public class SprintTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private Long sp;
}
