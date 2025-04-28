package com.NathanAzvdo.AcadPlanner.controller;

import com.NathanAzvdo.AcadPlanner.controller.Mapper.CursoMapper;
import com.NathanAzvdo.AcadPlanner.controller.Request.CursoRequest;
import com.NathanAzvdo.AcadPlanner.controller.Response.CursoResponse;
import com.NathanAzvdo.AcadPlanner.entity.Curso;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyListException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/curso")
public class CursoController{

    private CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    @PostMapping("/save")
    public ResponseEntity<CursoResponse> save(@RequestBody CursoRequest cursoRequest) {
        Curso toCurso = CursoMapper.toEntity(cursoRequest);
        Curso savedCurso = cursoService.save(toCurso);
        return ResponseEntity.ok(CursoMapper.toResponse(savedCurso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> findById(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.findById(id);
        if (curso.isEmpty()) {
            throw new InvalidIdException("Id inválido");
        }
        return ResponseEntity.ok(CursoMapper.toResponse(curso.get()));
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll() {
        Optional<List<Curso>> cursos = Optional.ofNullable(cursoService.listAll());

        if (cursos.isEmpty() || cursos.get().isEmpty()) {
            throw new EmptyListException("Nenhum curso encontrado.");
        }

        List<CursoResponse> responseList = cursos.get()
                .stream()
                .map(CursoMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<Curso> curso = cursoService.findById(id);
        if (curso.isEmpty()) {
            throw new InvalidIdException("Id inválido");
        }
        cursoService.deleteById(id);
        return ResponseEntity.ok("Curso deletado com sucesso!");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CursoResponse> update(@RequestBody CursoRequest cursoRequest, @PathVariable Long id) {
        Curso newCurso = CursoMapper.toEntity(cursoRequest);

        Curso updatedCurso = cursoService.update(newCurso, id)
                .orElseThrow(() -> new InvalidIdException("Curso não encontrado para atualização"));

        return ResponseEntity.ok().body(CursoMapper.toResponse(updatedCurso));
    }




}
