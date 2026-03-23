package com.NathanAzvdo.AcadPlanner.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@IdClass(InProgressSubjectId.class)
public class InProgressSubjects {

    @Id
    private Long userId;

    @Id
    private Long subjectId;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate estimatedCompletionDate;
}