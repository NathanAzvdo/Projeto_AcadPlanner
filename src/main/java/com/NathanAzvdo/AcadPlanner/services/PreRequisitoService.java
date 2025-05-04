package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreRequisitoService {

    private final MateriaRepository materiaRepository;

    public PreRequisitoService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public Materia addPreRequisito(Long materiaId, Long preRequisitoId) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada para o ID: " + materiaId));

        Materia preRequisito = materiaRepository.findById(preRequisitoId)
                .orElseThrow(() -> new RuntimeException("Pré-requisito não encontrado para o ID: " + preRequisitoId));

        materia.getPreRequisitos().add(preRequisito);
        return materiaRepository.save(materia);
    }

    public Materia removePreRequisito(Long materiaId, Long preRequisitoId) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada para o ID: " + materiaId));

        Materia preRequisito = materiaRepository.findById(preRequisitoId)
                .orElseThrow(() -> new RuntimeException("Pré-requisito não encontrado para o ID: " + preRequisitoId));

        materia.getPreRequisitos().remove(preRequisito);
        return materiaRepository.save(materia);
    }

    public List<Materia> getPreRequisitos(Long materiaId) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada para o ID: " + materiaId));

        if(materia.getPreRequisitos().isEmpty()){
            throw new RuntimeException("Nenhum pré-requisito encontrado para a matéria com ID: " + materiaId);
        }
        return materia.getPreRequisitos();
    }
}
