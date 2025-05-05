package com.NathanAzvdo.AcadPlanner.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MateriasConcluidasId implements Serializable {
    private Long usuarioId;
    private Long materiaId;
}
