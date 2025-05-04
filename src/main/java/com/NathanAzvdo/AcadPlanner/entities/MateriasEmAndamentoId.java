package com.NathanAzvdo.AcadPlanner.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class MateriasEmAndamentoId implements Serializable {
    private Long usuarioId;
    private Long materiaId;
}

