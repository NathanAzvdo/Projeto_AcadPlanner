package com.NathanAzvdo.AcadPlanner.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class MateriasConcluidas {

    @EmbeddedId
    private MateriasConcluidasId materiasConcluidasId;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @MapsId("materiaId")
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @Column(nullable = false)
    private LocalDate dataConclusao;
}
