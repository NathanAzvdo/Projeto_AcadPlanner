package com.NathanAzvdo.AcadPlanner.service;

import com.NathanAzvdo.AcadPlanner.entity.Materia;
import com.NathanAzvdo.AcadPlanner.repository.MateriaRepository;

import java.util.Optional;

public class MateriaService {

    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public Materia save(Materia newMateria) {
        return materiaRepository.save(newMateria);
    }

    public Optional<Materia> findById(Long id) {
        return materiaRepository.findById(id);
    }

    public void delete(Materia materia) {
        materiaRepository.delete(materia);
    }

    public void findAll() {
        materiaRepository.findAll();
    }

    public Optional<Materia> updateMateria(Materia materia) {
        Optional<Materia> optionalMateria = materiaRepository.findById(materia.getId());
        if (optionalMateria.isPresent()){
            Materia newMateria = optionalMateria.get();
            newMateria.setNome(materia.getNome());
            newMateria.setDescricao(materia.getDescricao());
            newMateria.setCreditos(materia.getCreditos());
            newMateria.setCursos(materia.getCursos());
            newMateria.setPreRequisitos(materia.getPreRequisitos());

            return Optional.of(materiaRepository.save(newMateria));
        }
        return Optional.empty();
    }
}
