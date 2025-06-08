package com.NathanAzvdo.AcadPlanner.repositories;

import com.NathanAzvdo.AcadPlanner.entities.MateriasEmAndamento;
import com.NathanAzvdo.AcadPlanner.entities.MateriasEmAndamentoId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaEmAndamentoRepository extends JpaRepository<MateriasEmAndamento, MateriasEmAndamentoId> {

    boolean existsByUsuarioIdAndMateriaId(Long id, Long materiaId);

    List<MateriasEmAndamento> findByUsuarioId(Long userId);

    @Modifying
    @Transactional
    void deleteByUsuarioIdAndMateriaId(Long id, Long materiaId);
}
