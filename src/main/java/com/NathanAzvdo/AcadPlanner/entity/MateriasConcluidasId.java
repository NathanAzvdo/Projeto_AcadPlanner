package com.NathanAzvdo.AcadPlanner.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MateriasConcluidasId implements Serializable {
    private Long usuarioId;
    private Long materiaId;
}
