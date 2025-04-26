package com.NathanAzvdo.AcadPlanner.controller;

import com.NathanAzvdo.AcadPlanner.controller.Response.CursoResponse;
import com.NathanAzvdo.AcadPlanner.service.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curso")
public class CursoController{

    private CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }


}
