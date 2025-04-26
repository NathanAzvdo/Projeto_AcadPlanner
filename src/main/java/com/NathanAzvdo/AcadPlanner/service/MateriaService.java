package com.NathanAzvdo.AcadPlanner.service;

import com.NathanAzvdo.AcadPlanner.entity.Materia;
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

    public Optional<Materia> findById(Long id) {
        Optional<Materia> materia = materiaRepository.findById(id);
        if (materia.isEmpty()) {
            throw new InvalidIdException("Id inválido!");
        }
        return materia;
    }

    public void deleteById(Long id) {
        Optional<Materia> searchMateria = materiaRepository.findById(id);
        if(searchMateria.isEmpty()){
            throw new InvalidIdException("Id inválido!");
        }
    }

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    public Optional<Materia> updateMateria(Materia materia, Long id) {
        Optional<Materia> optionalMateria = materiaRepository.findById(id);
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
