package com.NathanAzvdo.AcadPlanner.controllers;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaConcluidaMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaConcluidaResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.services.MateriaUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materia-user")
public class MateriaUsuarioController {

    private final MateriaUsuarioService materiaUsuarioService;

    public MateriaUsuarioController(MateriaUsuarioService materiaUsuarioService){
        this.materiaUsuarioService = materiaUsuarioService;
    }

    @PostMapping("/{idMateria}/concluir/{idUsuario}")
    public ResponseEntity<MateriaConcluidaResponse> concluirMateria(@PathVariable Long idMateria,@PathVariable Long idUsuario){
        return ResponseEntity.ok(MateriaConcluidaMapper.toResponse(materiaUsuarioService.concluirMateria(idMateria, idUsuario)));
    }

    @PostMapping("/{idMateria}/ingressar/{idUsuario}")
    public ResponseEntity<MateriaConcluidaResponse> concluirMateria(@PathVariable Long idMateria,@PathVariable Long idUsuario){
        return ResponseEntity.ok(MateriaConcluidaMapper.toResponse(materiaUsuarioService.concluirMateria(idMateria, idUsuario)));
    }


}
