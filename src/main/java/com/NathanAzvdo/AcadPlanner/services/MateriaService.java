package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.config.TokenService;
import com.NathanAzvdo.AcadPlanner.entities.*;
import com.NathanAzvdo.AcadPlanner.exceptions.BusinessException;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyListException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaConcluidaRepository;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaEmAndamentoRepository;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaRepository;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final MateriaConcluidaRepository materiaConcluidaRepository;
    private final UserRepository userRepository;
    private final MateriaEmAndamentoRepository materiaEmAndamentoRepository;
    private final TokenService tokenService;

    public MateriaService(MateriaRepository materiaRepository, MateriaConcluidaRepository materiaConcluidaRepository
    , UserRepository userRepository, MateriaEmAndamentoRepository materiaEmAndamentoRepository, TokenService tokenService) {
        this.materiaRepository= materiaRepository;
        this.materiaConcluidaRepository = materiaConcluidaRepository;
        this.userRepository = userRepository;
        this.materiaEmAndamentoRepository = materiaEmAndamentoRepository;
        this.tokenService = tokenService;
    }

    public MateriasEmAndamento novaMateriaEmAndamento(Long materiaId, HttpServletRequest request) {
        Long id = tokenService.getUsuarioIdFromRequest(request);

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

    public MateriasConcluidas concluirMateria(Long materiaId, HttpServletRequest request) {
        Long id = tokenService.getUsuarioIdFromRequest(request);
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

        materiaEmAndamentoRepository.deleteByUsuarioIdAndMateriaId(id, materiaId);

        return materiaConcluidaRepository.save(materiasConcluidas);
    }

    public List<Materia> findAll(HttpServletRequest request) {
        Long cursoId = tokenService.getCursoFromRequest(request);
        List<Materia> materias =  materiaRepository.findByCursosId(cursoId);
        if (materias.isEmpty()) {
            throw new EmptyListException("Nenhuma matéria encontrada.");
        }
        return materias;
    }

    public Materia findById(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Matéria não encontrada para o ID: " + id));
    }

    public List<Materia> getPreRequisitos(Long materiaId) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada para o ID: " + materiaId));

        if(materia.getPreRequisitos().isEmpty()){
            throw new RuntimeException("Nenhum pré-requisito encontrado para a matéria com ID: " + materiaId);
        }
        return materia.getPreRequisitos();
    }

    private void verificarMateriaAndamento(Long id, Long materiaId){
        boolean materiaEmAndamento = materiaEmAndamentoRepository.existsByUsuarioIdAndMateriaId(id, materiaId);
        if (!materiaEmAndamento) {
            throw new BusinessException("A matéria precisa estar em andamento para ser concluída");
        }
    }
}

