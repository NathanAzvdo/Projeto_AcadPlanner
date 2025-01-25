package com.NathanAzvdo.AcadPlanner.entity;

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
    private Long usuarioId;

    @Id
    private Long materiaId;

    @Column(nullable = false)
    private LocalDate dataConclusao;
}