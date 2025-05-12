package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.*;
import com.NathanAzvdo.AcadPlanner.exceptions.BusinessException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaConcluidaRepository;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaEmAndamentoRepository;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaRepository;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MateriaUsuarioService {

    private final MateriaRepository materiaRepository;
    private final MateriaConcluidaRepository materiaConcluidaRepository;
    private final UserRepository userRepository;
    private final MateriaEmAndamentoRepository materiaEmAndamentoRepository;

    public MateriaUsuarioService(MateriaRepository materiaRepository, MateriaConcluidaRepository materiaConcluidaRepository
    , UserRepository userRepository, MateriaEmAndamentoRepository materiaEmAndamentoRepository) {
        this.materiaRepository= materiaRepository;
        this.materiaConcluidaRepository = materiaConcluidaRepository;
        this.userRepository = userRepository;
        this.materiaEmAndamentoRepository = materiaEmAndamentoRepository;
    }

    public MateriasEmAndamento novaMateriaEmAndamento(Long id, Long materiaId) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new InvalidIdException("Usuário não encontrado com o ID: " + id));

        Materia materia = materiaRepository.findById(materiaId).orElseThrow(() ->
                new InvalidIdException("Matéria não encontrada com Id: " + materiaId));

        if (!materia.getPreRequisitos().isEmpty()) {
            for (Materia preRequisito : materia.getPreRequisitos()) {
                boolean concluiu = materiaConcluidaRepository.existsByMateriasConcluidasId_UsuarioIdAndMateriasConcluidasId_MateriaId(id, preRequisito.getId());
                if (!concluiu) {
                    throw new BusinessException("Você precisa concluir " + preRequisito.getNome() + " antes de cursar " + materia.getNome());
                }
            }
        }

        MateriasEmAndamentoId materiasEmAndamentoId = new MateriasEmAndamentoId(id, materiaId);
        MateriasEmAndamento materiasEmAndamento = new MateriasEmAndamento();
        materiasEmAndamento.setUsuarioId(user.getId());
        materiasEmAndamento.setMateriaId(materia.getId());
        materiasEmAndamento.setDataInicio(LocalDate.now());

        return materiaEmAndamentoRepository.save(materiasEmAndamento);
    }

    public MateriasConcluidas concluirMateria(Long id, Long materiaId) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new InvalidIdException("Usuário não encontrado com o ID: " + id));

        Materia materia = materiaRepository.findById(materiaId).orElseThrow(() ->
                new InvalidIdException("Matéria não encontrada com Id: " + materiaId));

        verificarMateriaAndamento(id, materiaId);

        MateriasConcluidasId materiasConcluidasId = new MateriasConcluidasId(id, materiaId);

        MateriasConcluidas materiasConcluidas = new MateriasConcluidas();
        materiasConcluidas.setUsuario(user);
        materiasConcluidas.setMateria(materia);
        materiasConcluidas.setDataConclusao(LocalDate.now());

        materiaEmAndamentoRepository.deleteByMateriaEmAndamentoId_UsuarioIdAndMateriaEmAndamentoId_MateriaId(id, materiaId);

        return materiaConcluidaRepository.save(materiasConcluidas);
    }

    private void verificarMateriaAndamento(Long id, Long materiaId){
        boolean materiaEmAndamento = materiaEmAndamentoRepository.existsByMateriaEmAndamentoId_UsuarioIdAndMateriaEmAndamentoId_MateriaId(id, materiaId);
        if (!materiaEmAndamento) {
            throw new BusinessException("A matéria precisa estar em andamento para ser concluída");
        }
    }
}

