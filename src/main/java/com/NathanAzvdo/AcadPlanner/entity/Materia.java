package com.NathanAzvdo.AcadPlanner.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private int creditos;

    @ManyToMany(mappedBy = "materias")
    private List<Curso> cursos;

    @ManyToMany
    @JoinTable(
            name = "materia_pre_requisito",
            joinColumns = @JoinColumn(name = "materia_id"),
            inverseJoinColumns = @JoinColumn(name = "pre_requisito_id")
    )
    private List<Materia> preRequisitos;
}