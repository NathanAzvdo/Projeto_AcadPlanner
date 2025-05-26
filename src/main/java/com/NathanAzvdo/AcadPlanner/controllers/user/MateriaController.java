package com.NathanAzvdo.AcadPlanner.controllers.user;

import com.NathanAzvdo.AcadPlanner.config.TokenService;
import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaBasicaResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.services.MateriaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService,
                             TokenService tokenService) {
        this.materiaService = materiaService;
    }


    @PostMapping("/concluir/{idMateria}")
    public ResponseEntity<String> concluirMateria(@PathVariable Long idMateria, HttpServletRequest request){
            materiaService.concluirMateria(idMateria, request);
            return ResponseEntity.ok().body("Matéria concluída com sucesso!");
    }

    @PostMapping("/ingressar/{idMateria}")
    public ResponseEntity<String> ingressarMateria(@PathVariable Long idMateria, HttpServletRequest request){
        materiaService.novaMateriaEmAndamento(idMateria, request);
        return ResponseEntity.ok().body("Ingressou na matéria com sucesso!");
    }

    @GetMapping("/concluidas")
    public ResponseEntity<List<MateriaBasicaResponse>> findMateriasConcluidas(HttpServletRequest request){
        List<MateriaBasicaResponse> materias = materiaService.materiasConcluidas(request).stream()
                .map(MateriaMapper::toMateriaBasicaResponse)
                .toList();
        return ResponseEntity.ok().body(materias);
    }

    @GetMapping("/materias-curso")
    public ResponseEntity<List<MateriaResponse>> findByCurso(HttpServletRequest request){
        List<MateriaResponse> materias = materiaService.findMateriasCurso(request).stream()
                .map(MateriaMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(materias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponse> findById(@PathVariable Long id) {
        Materia materia = materiaService.findById(id);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(materia));
    }

    @GetMapping("/{id}/pre-requisito")
    public ResponseEntity<List<MateriaResponse>> getPreRequisitos(@PathVariable Long id) {
        List<Materia> preRequisitos = materiaService.getPreRequisitos(id);
        List<MateriaResponse> response = preRequisitos.stream()
                .map(MateriaMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(response);
    }


}
