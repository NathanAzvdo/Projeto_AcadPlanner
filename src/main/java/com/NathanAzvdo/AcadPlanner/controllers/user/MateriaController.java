package com.NathanAzvdo.AcadPlanner.controllers.user;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaConcluidaMapper;
import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaEmAndamentoMapper;
import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaConcluidaResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaEmAndamentoRespose;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.services.MateriaService;
import com.NathanAzvdo.AcadPlanner.services.MateriaUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/materia")
public class MateriaController {

    private final MateriaUsuarioService materiaUsuarioService;
    private final MateriaService materiaService;

    public MateriaController(MateriaUsuarioService materiaUsuarioService, MateriaService materiaService){
        this.materiaUsuarioService = materiaUsuarioService;
        this.materiaService = materiaService;
    }

    @PostMapping("/{idMateria}/concluir/{idUsuario}")
    public ResponseEntity<MateriaConcluidaResponse> concluirMateria(@PathVariable Long idMateria,@PathVariable Long idUsuario){
        return ResponseEntity.ok(MateriaConcluidaMapper.toResponse(
                materiaUsuarioService.concluirMateria(
                        idMateria,
                        idUsuario))
        );
    }

    @PostMapping("/{idMateria}/ingressar/{idUsuario}")
    public ResponseEntity<MateriaEmAndamentoRespose> ingressarMateria(@PathVariable Long idMateria, @PathVariable Long idUsuario){
        return ResponseEntity.ok(MateriaEmAndamentoMapper.toResponse(
                materiaUsuarioService.novaMateriaEmAndamento(
                        idMateria,
                        idUsuario)
        ));
    }

    @GetMapping
    public ResponseEntity<List<MateriaResponse>> findAll() {
        List<Materia> materias = materiaService.findAll();
        List<MateriaResponse> response = materias.stream()
                .map(MateriaMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponse> findById(@PathVariable Long id) {
        Materia materia = materiaService.findById(id);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(materia));
    }

    @GetMapping("/{materiaId}/pre-requisito")
    public ResponseEntity<List<MateriaResponse>> getPreRequisitos(@PathVariable Long materiaId) {
        List<Materia> preRequisitos = materiaService.getPreRequisitos(materiaId);
        List<MateriaResponse> response = preRequisitos.stream()
                .map(MateriaMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(response);
    }


}
