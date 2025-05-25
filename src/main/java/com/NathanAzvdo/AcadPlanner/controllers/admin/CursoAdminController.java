package com.NathanAzvdo.AcadPlanner.controllers.admin;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.CursoMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.CursoRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CursoResponse;
import com.NathanAzvdo.AcadPlanner.entities.Curso;
import com.NathanAzvdo.AcadPlanner.services.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/curso")
public class CursoAdminController {

    private CursoService cursoService;

    public CursoAdminController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    @PostMapping()
    public ResponseEntity<CursoResponse> save(@RequestBody CursoRequest cursoRequest) {
        Curso toCurso = CursoMapper.toEntity(cursoRequest);
        Curso savedCurso = cursoService.save(toCurso);
        return ResponseEntity.ok(CursoMapper.toResponse(savedCurso));
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
