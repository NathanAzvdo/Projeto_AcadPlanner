package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.exceptions.BusinessException;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyListException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.MateriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaAdminService{

    private final MateriaRepository materiaRepository;

    public MateriaAdminService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public Materia save(Materia newMateria) {
        try {
            if (newMateria.getNome() == null || newMateria.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("O nome da matéria não pode ser nulo ou vazio");
            }

            if (newMateria.getNome().length() > 100) {
                throw new IllegalArgumentException("O nome da matéria não pode exceder 100 caracteres");
            }
            if (newMateria.getCreditos() <= 0) {
                throw new IllegalArgumentException("O número de créditos deve ser maior que zero");
            }
            if (newMateria.getCursos() == null || newMateria.getCursos().isEmpty()) {
                throw new IllegalArgumentException("A matéria deve estar associada a pelo menos um curso");
            }
            return materiaRepository.save(newMateria);
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }
    public void deleteById(Long id) {
        try {
            if (!materiaRepository.existsById(id)) {
                throw new InvalidIdException("Matéria não encontrada para o ID: " + id);
            }
            materiaRepository.deleteById(id);
        }catch (BusinessException e){
                throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public List<Materia> findAll() {
        try {
            List<Materia> materias = materiaRepository.findAll();
            if (materias.isEmpty()) {
                throw new EmptyListException("Nenhuma matéria encontrada.");
            }
            return materias;
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public Materia updateMateria(Materia materia, Long id) {
        try {
            Materia materiaAtual = materiaRepository.findById(id)
                    .orElseThrow(() -> new InvalidIdException("Matéria não encontrada para o ID: " + id));

            atualizarCamposValidos(materiaAtual, materia);

            return materiaRepository.save(materiaAtual);
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public Materia addPreRequisito(Long materiaId, Long preRequisitoId) {
        try {
            Materia materia = materiaRepository.findById(materiaId)
                    .orElseThrow(() -> new RuntimeException("Matéria não encontrada para o ID: " + materiaId));

            Materia preRequisito = materiaRepository.findById(preRequisitoId)
                    .orElseThrow(() -> new RuntimeException("Pré-requisito não encontrado para o ID: " + preRequisitoId));

            materia.getPreRequisitos().add(preRequisito);
            return materiaRepository.save(materia);
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public Materia removePreRequisito(Long materiaId, Long preRequisitoId) {
        try {
            Materia materia = materiaRepository.findById(materiaId)
                    .orElseThrow(() -> new RuntimeException("Matéria não encontrada para o ID: " + materiaId));

            Materia preRequisito = materiaRepository.findById(preRequisitoId)
                    .orElseThrow(() -> new RuntimeException("Pré-requisito não encontrado para o ID: " + preRequisitoId));

            materia.getPreRequisitos().remove(preRequisito);
            return materiaRepository.save(materia);
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    private void atualizarCamposValidos(Materia materiaAtual, Materia novaMateria) {
        materiaAtual.setNome(novaMateria.getNome() != null ? novaMateria.getNome() : materiaAtual.getNome());
        materiaAtual.setDescricao(novaMateria.getDescricao() != null ? novaMateria.getDescricao() : materiaAtual.getDescricao());
        materiaAtual.setCreditos(novaMateria.getCreditos() != 0 ? novaMateria.getCreditos() : materiaAtual.getCreditos());
        materiaAtual.setCursos(!novaMateria.getCursos().isEmpty() ? novaMateria.getCursos() : materiaAtual.getCursos());
    }
}
