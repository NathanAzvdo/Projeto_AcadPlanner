package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.Curso;
import com.NathanAzvdo.AcadPlanner.exceptions.BusinessException;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyListException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository){
        this.cursoRepository = cursoRepository;
    }

    public Curso save(Curso curso) {
            validateCurso(curso);
            return cursoRepository.save(curso);

    }



    public Optional<Object> findByNome(String nome) {
        return cursoRepository.findByNome(nome);
    }

    private void validateCurso(Curso curso) {
        if (curso == null) {
            throw new BusinessException("O curso não pode ser nulo");
        }

        Optional.ofNullable(curso.getNome())
                .filter(nome -> !nome.trim().isEmpty())
                .filter(nome -> nome.length() <= 100)
                .orElseThrow(() -> new BusinessException("Nome do curso é obrigatório e deve ter no máximo 100 caracteres"));

        Optional.ofNullable(curso.getDescricao())
                .ifPresent(descricao -> {
                    if (descricao.length() > 500) {
                        throw new BusinessException("Descrição do curso deve ter no máximo 500 caracteres");
                    }
                });

        if (cursoRepository.findByNome(curso.getNome()).isPresent()) {
            throw new BusinessException("Já existe um curso com este nome");
        }
    }

    public List<Curso> listAll(){
            List<Curso> cursos = cursoRepository.findAll();
            if (cursos.isEmpty()) {
                throw new EmptyListException("Nenhum curso encontrado.");
            }
            return cursos;

    }

    public Curso findById(Long id){
            return cursoRepository.findById(id)
                    .orElseThrow(() -> new InvalidIdException("Curso com ID " + id + " não encontrado"));
    }

    public Curso findUserCourse(Long cursoId){
            if (cursoId == null) {
                throw new InvalidIdException("Curso não encontrado para o usuário");
            }
            return cursoRepository.findById(cursoId)
                    .orElseThrow(() -> new InvalidIdException("Curso não encontrado"));
    }

    public void deleteById(Long id){
            Optional<Curso> curso = cursoRepository.findById(id);
            if (curso.isEmpty()) {
                throw new InvalidIdException("Id inválido!");
            }
            cursoRepository.deleteById(id);

    }

    public Curso update(Curso curso, Long id) {
            Curso cursoToUpdate = cursoRepository.findById(id)
                    .orElseThrow(() -> new InvalidIdException("Curso com ID " + id + " não encontrado para atualização"));

            Optional.ofNullable(curso.getNome()).ifPresent(cursoToUpdate::setNome);
            Optional.ofNullable(curso.getDescricao()).ifPresent(cursoToUpdate::setDescricao);

            Optional.ofNullable(curso.getUsuarios())
                    .filter(usuarios -> !usuarios.isEmpty())
                    .ifPresent(cursoToUpdate::setUsuarios);

            Optional.ofNullable(curso.getMaterias())
                    .filter(materias -> !materias.isEmpty())
                    .ifPresent(cursoToUpdate::setMaterias);

            return cursoRepository.save(cursoToUpdate);

    }
}