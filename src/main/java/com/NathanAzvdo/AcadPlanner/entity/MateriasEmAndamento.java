package com.NathanAzvdo.AcadPlanner.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@IdClass(MateriasEmAndamentoId.class)
public class MateriasEmAndamento {

    @Id
    private Long usuarioId;

    @Id
    private Long materiaId;

    @Column(nullable = false)
    private LocalDate dataInicio;

    private LocalDate previsaoConclusao;
}