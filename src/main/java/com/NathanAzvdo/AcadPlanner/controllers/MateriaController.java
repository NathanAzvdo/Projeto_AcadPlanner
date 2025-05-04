package com.NathanAzvdo.AcadPlanner.controllers;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.MateriaRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.services.PreRequisitoService;
import org.springframework.http.ResponseEntity;
import com.NathanAzvdo.AcadPlanner.services.MateriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    private final MateriaService materiaService;
    private final PreRequisitoService preRequisitoService;

    public MateriaController(MateriaService materiaService, PreRequisitoService preRequisitoService) {
        this.materiaService = materiaService;
        this.preRequisitoService = preRequisitoService;
    }

    @PostMapping("/save")
    public ResponseEntity<MateriaResponse> save(@RequestBody MateriaRequest materiaRequest) {
        Materia newMateria = MateriaMapper.toMateria(materiaRequest);
        Materia materia = materiaService.save(newMateria);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(materia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        materiaService.deleteById(id);
        return ResponseEntity.ok().body("Matéria deletada com sucesso!");
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

    @PatchMapping("/{id}")
    public ResponseEntity<MateriaResponse> update(@RequestBody MateriaRequest materiaRequest, @PathVariable Long id) {
        Materia newMateria = MateriaMapper.toMateria(materiaRequest);
        Materia updatedMateria = materiaService.updateMateria(newMateria, id);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(updatedMateria));
    }

    @PostMapping("/{materiaId}/pre-requisito/{preRequisitoId}")
    public ResponseEntity<MateriaResponse> addPreRequisito(@PathVariable Long materiaId, @PathVariable Long preRequisitoId) {
        Materia updatedMateria = preRequisitoService.addPreRequisito(materiaId, preRequisitoId);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(updatedMateria));
    }

    @DeleteMapping("/{materiaId}/pre-requisito/{preRequisitoId}")
    public ResponseEntity<MateriaResponse> removePreRequisito(@PathVariable Long materiaId, @PathVariable Long preRequisitoId) {
        Materia updatedMateria = preRequisitoService.removePreRequisito(materiaId, preRequisitoId);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(updatedMateria));
    }

    @GetMapping("/{materiaId}/pre-requisito")
    public ResponseEntity<List<MateriaResponse>> getPreRequisitos(@PathVariable Long materiaId) {
        List<Materia> preRequisitos = preRequisitoService.getPreRequisitos(materiaId);
        List<MateriaResponse> response = preRequisitos.stream()
                .map(MateriaMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(response);
    }
}
