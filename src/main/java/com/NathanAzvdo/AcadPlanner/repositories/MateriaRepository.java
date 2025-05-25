package com.NathanAzvdo.AcadPlanner.repositories;

import com.NathanAzvdo.AcadPlanner.entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findByCursosId(Long cursoId);
}
