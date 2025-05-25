package com.NathanAzvdo.AcadPlanner.repositories;

import com.NathanAzvdo.AcadPlanner.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Object> findByNome(String nome);
}
