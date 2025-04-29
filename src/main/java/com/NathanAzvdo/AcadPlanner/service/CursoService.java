package com.NathanAzvdo.AcadPlanner.service;


import com.NathanAzvdo.AcadPlanner.entity.Curso;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyListException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final MateriaService materiaService;

    public CursoService(CursoRepository cursoRepository, MateriaService materiaService){
        this.cursoRepository = cursoRepository;
        this.materiaService =  materiaService;

    }

    public Curso save(Curso curso){
        return cursoRepository.save(curso);
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

    public void deleteById(Long id){
        Optional<Curso> curso = cursoRepository.findById(id);
        if(curso.isEmpty()){
            throw new InvalidIdException("Id inválido!");
        }
        cursoRepository.deleteById(id);
    }

    public Curso update(Curso curso, Long id) {
        return cursoRepository.findById(id)
                .map(cursoToUpdate -> {
                    Optional.ofNullable(curso.getNome()).ifPresent(cursoToUpdate::setNome);
                    Optional.ofNullable(curso.getDescricao()).ifPresent(cursoToUpdate::setDescricao);

                    Optional.ofNullable(curso.getUsuarios())
                            .filter(usuarios -> !usuarios.isEmpty())
                            .ifPresent(cursoToUpdate::setUsuarios);

                    Optional.ofNullable(curso.getMaterias())
                            .filter(materias -> !materias.isEmpty())
                            .ifPresent(cursoToUpdate::setMaterias);

                    return cursoRepository.save(cursoToUpdate);
                })
                .orElseThrow(() -> new InvalidIdException("Curso com ID " + id + " não encontrado para atualização"));
    }

}

























