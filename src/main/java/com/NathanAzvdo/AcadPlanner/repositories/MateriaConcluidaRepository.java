package com.NathanAzvdo.AcadPlanner.repositories;

import com.NathanAzvdo.AcadPlanner.entities.MateriasConcluidas;
import com.NathanAzvdo.AcadPlanner.entities.MateriasConcluidasId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriaConcluidaRepository extends JpaRepository<MateriasConcluidas, MateriasConcluidasId> {
    List<MateriasConcluidas> findByUsuarioId(Long userId);
}
