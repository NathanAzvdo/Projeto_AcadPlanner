package com.NathanAzvdo.AcadPlanner.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "materias_em_andamento")
@IdClass(InProgressSubjectId.class)
public class InProgressSubjects {

    @Id
    @Column(name = "usuario_id")
    private Long userId;

    @Id
    @Column(name = "materia_id")
    private Long subjectId;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate startDate;

    @Column(name = "previsao_conclusao")
    private LocalDate estimatedCompletionDate;
}