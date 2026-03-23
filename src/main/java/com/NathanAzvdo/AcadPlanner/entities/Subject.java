package com.NathanAzvdo.AcadPlanner.entities;

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
@Table(name = "materia")
@Builder
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;

    @Column(name = "descricao")
    private String description;

    @Column(name = "creditos", nullable = false)
    private int credits;

    @ManyToMany
    @JoinTable(
            name = "curso_materia",
            joinColumns = @JoinColumn(name = "materia_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    @JsonIgnore
    private List<Course> courses;

    @ManyToMany
    @JoinTable(
            name = "materia_pre_requisito",
            joinColumns = @JoinColumn(name = "materia_id"),
            inverseJoinColumns = @JoinColumn(name = "pre_requisito_id")
    )
    @JsonIgnore
    private List<Subject> prerequisites;
}