package com.NathanAzvdo.AcadPlanner.controllers.admin;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.MateriaRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entities.Materia;
import org.springframework.http.ResponseEntity;
import com.NathanAzvdo.AcadPlanner.services.MateriaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/materia")
public class MateriaAdminController {

    private final MateriaService materiaService;

    public MateriaAdminController(MateriaService materiaService) {
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

    @PatchMapping("/{id}")
    public ResponseEntity<MateriaResponse> update(@RequestBody MateriaRequest materiaRequest, @PathVariable Long id) {
        Materia newMateria = MateriaMapper.toMateria(materiaRequest);
        Materia updatedMateria = materiaService.updateMateria(newMateria, id);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(updatedMateria));
    }

    @PostMapping("/{materiaId}/pre-requisito/{preRequisitoId}")
    public ResponseEntity<MateriaResponse> addPreRequisito(@PathVariable Long materiaId, @PathVariable Long preRequisitoId) {
        Materia updatedMateria = materiaService.addPreRequisito(materiaId, preRequisitoId);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(updatedMateria));
    }

    @DeleteMapping("/{materiaId}/pre-requisito/{preRequisitoId}")
    public ResponseEntity<MateriaResponse> removePreRequisito(@PathVariable Long materiaId, @PathVariable Long preRequisitoId) {
        Materia updatedMateria = materiaService.removePreRequisito(materiaId, preRequisitoId);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(updatedMateria));
    }

}
