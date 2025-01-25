package com.NathanAzvdo.AcadPlanner.repository;

import com.NathanAzvdo.AcadPlanner.entity.MateriasEmAndamento;
import com.NathanAzvdo.AcadPlanner.entity.MateriasEmAndamentoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaAndamentoRepository extends JpaRepository<MateriasEmAndamento, MateriasEmAndamentoId> {
    List<MateriasEmAndamento> findByUsuarioId(Long userId);
}
