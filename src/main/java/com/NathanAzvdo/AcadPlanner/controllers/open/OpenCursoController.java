package com.NathanAzvdo.AcadPlanner.controllers.open;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.CursoMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CursoBasicoResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import com.NathanAzvdo.AcadPlanner.services.CursoService;

// Imports do Swagger/Springdoc
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cursos") // Mudança de /cursos para /open/cursos seria uma boa prática, mas /cursos tá ok
@Tag(name = "Cursos (Público)", description = "Endpoints públicos para visualização de cursos (ex: para tela de registro)")
public class OpenCursoController {

    private CursoService cursoService;

    public OpenCursoController (CursoService cursoService){
        this.cursoService = cursoService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os cursos (básico)",
            description = "Retorna o ID e o Nome de todos os cursos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cursos recuperada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            // Indica que a resposta é um array (lista)
                            array = @ArraySchema(schema = @Schema(implementation = CursoBasicoResponse.class))) }),

            // Baseado no seu ControllerAdvice (EmptyListException -> 404)
            @ApiResponse(responseCode = "404", description = "Nenhum curso encontrado (EmptyListException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<List<CursoBasicoResponse>> getCursos(){
        List<CursoBasicoResponse> cursos = cursoService.listAll().stream()
                .map(CursoMapper::toBasicoResponse).toList();
        return ResponseEntity.ok(cursos);

    }
}