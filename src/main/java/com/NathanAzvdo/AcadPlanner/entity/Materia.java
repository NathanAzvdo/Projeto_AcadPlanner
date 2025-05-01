package com.NathanAzvdo.AcadPlanner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private int creditos;

    @ManyToMany
    @JoinTable(
            name = "curso_materia",
            joinColumns = @JoinColumn(name = "materia_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    @JsonIgnore
    private List<Curso> cursos;

    @ManyToMany
    @JoinTable(
            name = "materia_pre_requisito",
            joinColumns = @JoinColumn(name = "materia_id"),
            inverseJoinColumns = @JoinColumn(name = "pre_requisito_id")
    )
    @JsonIgnore
    private List<Materia> preRequisitos;
}