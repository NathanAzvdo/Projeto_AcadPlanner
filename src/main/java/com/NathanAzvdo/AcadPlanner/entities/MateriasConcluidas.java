package com.NathanAzvdo.AcadPlanner.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@IdClass(MateriasConcluidasId.class)
public class MateriasConcluidas {

    @Id
    @ManyToOne
    private Long usuarioId;

    @Id
    @ManyToOne
    private Long materiaId;

    @Column(nullable = false)
    private LocalDate dataConclusao;
}