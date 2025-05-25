package com.NathanAzvdo.AcadPlanner.controllers.user;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.CursoMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CursoResponse;
import com.NathanAzvdo.AcadPlanner.entities.Curso;
import com.NathanAzvdo.AcadPlanner.services.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/curso")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
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

}
