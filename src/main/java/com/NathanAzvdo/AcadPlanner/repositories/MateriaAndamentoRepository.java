package com.NathanAzvdo.AcadPlanner.repositories;

import com.NathanAzvdo.AcadPlanner.entities.MateriasEmAndamento;
import com.NathanAzvdo.AcadPlanner.entities.MateriasEmAndamentoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaAndamentoRepository extends JpaRepository<MateriasEmAndamento, MateriasEmAndamentoId> {
    List<MateriasEmAndamento> findByUsuarioId(Long userId);
}
