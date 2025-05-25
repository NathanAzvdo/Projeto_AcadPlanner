package com.NathanAzvdo.AcadPlanner.controllers.user;

import com.NathanAzvdo.AcadPlanner.config.TokenService;
import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaConcluidaMapper;
import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaEmAndamentoMapper;
import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaConcluidaResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaEmAndamentoRespose;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.services.MateriaAdminService;
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
    public ResponseEntity<MateriaConcluidaResponse> concluirMateria(@PathVariable Long idMateria, HttpServletRequest request){
        return ResponseEntity.ok(MateriaConcluidaMapper.toResponse(
                materiaService.concluirMateria(
                        idMateria, request))
        );
    }

    @PostMapping("/ingressar/{idMateria}")
    public ResponseEntity<MateriaEmAndamentoRespose> ingressarMateria(@PathVariable Long idMateria, HttpServletRequest request){
        return ResponseEntity.ok(MateriaEmAndamentoMapper.toResponse(
                materiaService.novaMateriaEmAndamento(
                        idMateria,
                        request))
        );
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
