package com.NathanAzvdo.AcadPlanner.service;

import com.NathanAzvdo.AcadPlanner.entity.Materia;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public Materia save(Materia newMateria) {
        return materiaRepository.save(newMateria);
    }

    public Materia findById(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Matéria não encontrada para o ID: " + id));
    }

    public void deleteById(Long id) {
        if (!materiaRepository.existsById(id)) {
            throw new InvalidIdException("Matéria não encontrada para o ID: " + id);
        }
        materiaRepository.deleteById(id);
    }

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    public Materia updateMateria(Materia materia, Long id) {
        Materia existingMateria = materiaRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Matéria não encontrada para o ID: " + id));

        existingMateria.setNome(materia.getNome());
        existingMateria.setDescricao(materia.getDescricao());
        existingMateria.setCreditos(materia.getCreditos());
        existingMateria.setCursos(materia.getCursos());
        existingMateria.setPreRequisitos(materia.getPreRequisitos());

        return materiaRepository.save(existingMateria);
    }
}
