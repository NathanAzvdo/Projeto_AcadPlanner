package com.NathanAzvdo.AcadPlanner.repositories;

import com.NathanAzvdo.AcadPlanner.entities.MateriasEmAndamento;
import com.NathanAzvdo.AcadPlanner.entities.MateriasEmAndamentoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaEmAndamentoRepository extends JpaRepository<MateriasEmAndamento, MateriasEmAndamentoId> {
    boolean existsByMateriaEmAndamentoId_UsuarioIdAndMateriaEmAndamentoId_MateriaId(Long id, Long materiaId);

    void deleteByMateriaEmAndamentoId_UsuarioIdAndMateriaEmAndamentoId_MateriaId(Long id, Long materiaId);
}
