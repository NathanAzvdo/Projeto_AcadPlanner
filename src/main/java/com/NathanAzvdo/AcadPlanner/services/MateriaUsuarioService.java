package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.entities.MateriasConcluidas;
import com.NathanAzvdo.AcadPlanner.entities.MateriasConcluidasId;
import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaConcluidaRepository;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaRepository;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MateriaUsuarioService {

    private final MateriaRepository materiaRepository;
    private final MateriaConcluidaRepository materiaConcluidaRepository;
    private final UserRepository userRepository;

    public MateriaUsuarioService(MateriaRepository materiaRepository, MateriaConcluidaRepository materiaConcluidaRepository
    , UserRepository userRepository) {
        this.materiaRepository= materiaRepository;
        this.materiaConcluidaRepository = materiaConcluidaRepository;
        this.userRepository = userRepository;
    }

    public MateriasConcluidas concluirMateria(Long id, Long materiaId) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new InvalidIdException("Usuário não encontrado com o ID: " + id));

        Materia materia = materiaRepository.findById(materiaId).orElseThrow(() ->
                new InvalidIdException("Matéria não encontrada com Id: " + materiaId));

        MateriasConcluidasId materiasConcluidasId = new MateriasConcluidasId(id, materiaId);

        MateriasConcluidas materiasConcluidas = new MateriasConcluidas();
        materiasConcluidas.setMateriaId(materiasConcluidasId.getMateriaId());
        materiasConcluidas.setUsuarioId(user.getId());
        materiasConcluidas.setMateriaId(materia.getId());
        materiasConcluidas.setDataConclusao(LocalDate.now());

        return materiaConcluidaRepository.save(materiasConcluidas);
    }

}
