package com.NathanAzvdo.AcadPlanner.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MateriasConcluidasId implements Serializable {
    private Long usuarioId;
    private Long materiaId;
}
