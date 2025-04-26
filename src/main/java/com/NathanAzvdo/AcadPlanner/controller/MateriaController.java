package com.NathanAzvdo.AcadPlanner.controller;

import com.NathanAzvdo.AcadPlanner.controller.Mapper.MateriaMapper;
import com.NathanAzvdo.AcadPlanner.controller.Request.MateriaRequest;
import com.NathanAzvdo.AcadPlanner.controller.Response.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entity.Materia;
import org.springframework.http.ResponseEntity;
import com.NathanAzvdo.AcadPlanner.service.MateriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    private MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
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
}
