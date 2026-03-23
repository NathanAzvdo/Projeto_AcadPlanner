package com.NathanAzvdo.AcadPlanner.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class CompletedSubject {

    @EmbeddedId
    private CompletedSubjectsId completedSubjectsId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "usuario_id")
    private User user;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "materia_id")
    private Subject subject;

    @Column(nullable = false)
    private LocalDate completionDate;
}
