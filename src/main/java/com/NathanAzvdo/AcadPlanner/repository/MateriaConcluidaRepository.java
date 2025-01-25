package com.NathanAzvdo.AcadPlanner.repository;

import com.NathanAzvdo.AcadPlanner.entity.MateriasConcluidas;
import com.NathanAzvdo.AcadPlanner.entity.MateriasConcluidasId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriaConcluidaRepository extends JpaRepository<MateriasConcluidas, MateriasConcluidasId> {
    List<MateriasConcluidas> findByUsuarioId(Long userId);
}
