package com.NathanAzvdo.AcadPlanner.controller;


import com.NathanAzvdo.AcadPlanner.controller.Mapper.MateriaMapper;
import com.NathanAzvdo.AcadPlanner.controller.Request.MateriaRequest;
import com.NathanAzvdo.AcadPlanner.controller.Response.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entity.Materia;
import com.NathanAzvdo.AcadPlanner.service.MateriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService){
        this.materiaService = materiaService;
    }

    @PostMapping("/save")
    public ResponseEntity<MateriaResponse> save(@RequestBody MateriaRequest materiaRequest){
        Materia newMateria = MateriaMapper.toMateria(materiaRequest);
        Materia materia = materiaService.save(newMateria);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(materia));
    }

    @DeleteMapping

}
