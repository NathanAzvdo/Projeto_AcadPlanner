package com.NathanAzvdo.AcadPlanner.service;

import com.NathanAzvdo.AcadPlanner.entity.Materia;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyListException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        List<Materia> materias =  materiaRepository.findAll();
        if (materias.isEmpty()) {
            throw new EmptyListException("Nenhum curso encontrado.");
        }
        return materias;
    }

    public Materia updateMateria(Materia materia, Long id) {
        return materiaRepository.findById(id)
                .map(materiaToUpdate -> {
                    Optional.ofNullable(materia.getNome()).ifPresent(materiaToUpdate::setNome);
                    Optional.ofNullable(materia.getDescricao()).ifPresent(materiaToUpdate::setDescricao);

                    Optional.of(materia.getCreditos())
                            .filter(creditos -> creditos != 0)
                            .ifPresent(materiaToUpdate::setCreditos);

                    Optional.ofNullable(materia.getCursos())
                            .filter(cursos -> !cursos.isEmpty())
                            .ifPresent(materiaToUpdate::setCursos);

                    return materiaRepository.save(materiaToUpdate);
                })
                .orElseThrow(() -> new InvalidIdException("Matéria não encontrada para o ID: " + id));
    }
}
