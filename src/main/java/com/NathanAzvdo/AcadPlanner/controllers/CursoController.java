package com.NathanAzvdo.AcadPlanner.controllers;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.CursoMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.CursoRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CursoResponse;
import com.NathanAzvdo.AcadPlanner.entities.Curso;
import com.NathanAzvdo.AcadPlanner.services.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/curso")
public class CursoController{

    private CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    @PostMapping()
    public ResponseEntity<CursoResponse> save(@RequestBody CursoRequest cursoRequest) {
        Curso toCurso = CursoMapper.toEntity(cursoRequest);
        Curso savedCurso = cursoService.save(toCurso);
        return ResponseEntity.ok(CursoMapper.toResponse(savedCurso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> findById(@PathVariable Long id) {
        Curso curso = cursoService.findById(id);
        return ResponseEntity.ok(CursoMapper.toResponse(curso));
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll() {
        List<Curso> cursos = cursoService.listAll();

        List<CursoResponse> responseList = cursos
                .stream()
                .map(CursoMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Curso curso = cursoService.findById(id);
        cursoService.deleteById(id);
        return ResponseEntity.ok("Curso deletado com sucesso!");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CursoResponse> update(@RequestBody CursoRequest cursoRequest, @PathVariable Long id) {
        Curso newCurso = CursoMapper.toEntity(cursoRequest);
        Curso updatedCurso = cursoService.update(newCurso, id);
        return ResponseEntity.ok().body(CursoMapper.toResponse(updatedCurso));
    }





}
