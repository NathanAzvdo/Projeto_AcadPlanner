package com.NathanAzvdo.AcadPlanner.controllers.open;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.CourseMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CourseBasicResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import com.NathanAzvdo.AcadPlanner.services.CourseService;

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
@RequestMapping("/courses") // Mudança de /courses para /open/courses seria uma boa prática, mas /courses tá ok
@Tag(name = "Cursos (Público)", description = "Endpoints públicos para visualização de courses (ex: para tela de registro)")
public class OpenCourseController {

    private CourseService courseService;

    public OpenCourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os courses (básico)",
            description = "Retorna o ID e o Nome de todos os courses cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de courses recuperada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            // Indica que name resposta é um array (lista)
                            array = @ArraySchema(schema = @Schema(implementation = CourseBasicResponse.class))) }),

            // Baseado no seu ControllerAdvice (EmptyListException -> 404)
            @ApiResponse(responseCode = "404", description = "Nenhum course encontrado (EmptyListException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<List<CourseBasicResponse>> getCursos(){
        List<CourseBasicResponse> cursos = courseService.listAll().stream()
                .map(CourseMapper::toBasicoResponse).toList();
        return ResponseEntity.ok(cursos);

    }
}