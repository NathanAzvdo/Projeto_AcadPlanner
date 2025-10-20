package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.*;
import com.NathanAzvdo.AcadPlanner.exceptions.BusinessException;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyListException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaConcluidaRepository;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaEmAndamentoRepository;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaRepository;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final MateriaConcluidaRepository materiaConcluidaRepository;
    private final UserRepository userRepository;
    private final MateriaEmAndamentoRepository materiaEmAndamentoRepository;
    public MateriaService(MateriaRepository materiaRepository, MateriaConcluidaRepository materiaConcluidaRepository
            , UserRepository userRepository, MateriaEmAndamentoRepository materiaEmAndamentoRepository) {
        this.materiaRepository= materiaRepository;
        this.materiaConcluidaRepository = materiaConcluidaRepository;
        this.userRepository = userRepository;
        this.materiaEmAndamentoRepository = materiaEmAndamentoRepository;
    }

    // Método alterado para receber User
    public void novaMateriaEmAndamento(Long materiaId, User user) {
        Long usuarioId = user.getId();

        Materia materia = materiaRepository.findById(materiaId).orElseThrow(() ->
                new InvalidIdException("Matéria não encontrada com Id: " + materiaId));

        if (verificarMateriaAndamento(usuarioId, materiaId)) {
            throw new BusinessException("Você já está cursando a matéria " + materia.getNome() + ".");
        }

        for (Materia preRequisito : materia.getPreRequisitos()) {
            boolean concluiu = materiaConcluidaRepository
                    .existsByMateriasConcluidasId_UsuarioIdAndMateriasConcluidasId_MateriaId(usuarioId, preRequisito.getId());
            if (!concluiu) {
                throw new BusinessException("Você precisa concluir " + preRequisito.getNome() +
                        " antes de cursar " + materia.getNome() + ".");
            }
        }

        MateriasEmAndamento materiasEmAndamento = new MateriasEmAndamento();
        materiasEmAndamento.setUsuarioId(user.getId());
        materiasEmAndamento.setMateriaId(materia.getId());
        materiasEmAndamento.setDataInicio(LocalDate.now());

        materiaEmAndamentoRepository.save(materiasEmAndamento);
    }

    @Transactional
    public void concluirMateria(Long materiaId, User user) {
        if (materiaId == null) {
            throw new InvalidIdException("ID da matéria não pode ser nulo");
        }

        Long usuarioId = user.getId();

        if (usuarioId == null) {
            throw new BusinessException("Não foi possível identificar o usuário");
        }

        Materia materia = materiaRepository.findById(materiaId).orElseThrow(() ->
                new InvalidIdException("Matéria não encontrada com Id: " + materiaId));

        if (!verificarMateriaAndamento(usuarioId, materiaId)) {
            throw new BusinessException("Você não está cursando a matéria " + materia.getNome() + ".");
        }

        MateriasConcluidasId materiasConcluidasId = new MateriasConcluidasId(usuarioId, materiaId);

        MateriasConcluidas materiasConcluidas = new MateriasConcluidas();
        materiasConcluidas.setMateriasConcluidasId(materiasConcluidasId);
        materiasConcluidas.setUsuario(user);
        materiasConcluidas.setMateria(materia);
        materiasConcluidas.setDataConclusao(LocalDate.now());

        materiaEmAndamentoRepository.deleteByUsuarioIdAndMateriaId(usuarioId, materiaId);
        materiaConcluidaRepository.save(materiasConcluidas);
    }

    public List<Materia> findMateriasCurso(Long cursoId) {
        try {
            List<Materia> materias = materiaRepository.findByCursosId(cursoId);
            if (materias.isEmpty()) {
                throw new EmptyListException("Nenhuma matéria encontrada.");
            }
            return materias;
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public List<MateriasConcluidas> materiasConcluidas(Long usuarioId) {
        try {
            List<MateriasConcluidas> materiasConcluidas = materiaConcluidaRepository.findByUsuarioId(usuarioId);
            if (materiasConcluidas.isEmpty()) {
                throw new EmptyListException("Nenhuma matéria concluída encontrada.");
            }
            return materiasConcluidas;
        } catch (BusinessException e) {
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public Materia findById(Long id) {
        try{
            return materiaRepository.findById(id)
                    .orElseThrow(() -> new InvalidIdException("Matéria não encontrada para o ID: " + id));
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public List<Materia> getPreRequisitos(Long materiaId) {
        try {
            Materia materia = materiaRepository.findById(materiaId)
                    .orElseThrow(() -> new RuntimeException("Matéria não encontrada"));

            if (materia.getPreRequisitos().isEmpty()) {
                throw new EmptyListException("Nenhum pré-requisito encontrado para a matéria. ");
            }
            return materia.getPreRequisitos();
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    private boolean verificarMateriaAndamento(Long id, Long materiaId){
        try {
            boolean materiaEmAndamento = materiaEmAndamentoRepository.existsByUsuarioIdAndMateriaId(id, materiaId);
            if (materiaEmAndamento) {
                return true;
            }
            return false;
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }
}