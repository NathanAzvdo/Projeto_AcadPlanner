package com.NathanAzvdo.AcadPlanner.service;


import com.NathanAzvdo.AcadPlanner.entity.Curso;
import com.NathanAzvdo.AcadPlanner.entity.Materia;
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
        return cursoRepository.findAll();
    }

    public Optional<Curso> findById(Long id){
        return cursoRepository.findById(id);
    }

    public void delete(Curso curso){
        cursoRepository.delete(curso);
    }

    public Optional<Curso> update(Curso curso){
        Optional<Curso> optionalCurso = cursoRepository.findById(curso.getId());
        if(optionalCurso.isPresent()){
            Curso cursoToUpdate = optionalCurso.get();
            cursoToUpdate.setNome(curso.getNome());
            cursoToUpdate.setDescricao(curso.getDescricao());
            return Optional.of(cursoRepository.save(cursoToUpdate));
        }
        return Optional.empty();
    }

    public Curso addMateria(Long cursoId, Long materiaId){
        Optional<Curso> optionalCurso = cursoRepository.findById(cursoId);
        if(optionalCurso.isPresent()){
            Curso curso = optionalCurso.get();
            Optional<Materia> materia = materiaService.findById(materiaId);
            if(materia.isPresent()){
                curso.getMaterias().add(materia.get());
                return cursoRepository.save(curso);
            }
            else{
                return null;
            }
        }
        return null;
    }

    public Curso deleteMateria(Long cursoId, Long materiaId){
        Optional<Curso> optionalCurso = cursoRepository.findById(cursoId);
        if(optionalCurso.isPresent()){
            Curso curso = optionalCurso.get();
            Optional<Materia> materia = materiaService.findById(materiaId);
            if(materia.isPresent()){
                curso.getMaterias().remove(materia.get());
                return cursoRepository.save(curso);
            }
        }
        return null;
    }

}
