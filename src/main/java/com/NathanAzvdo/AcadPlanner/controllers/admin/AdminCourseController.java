package com.NathanAzvdo.AcadPlanner.controllers.admin;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.CourseMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.CourseRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CourseResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import com.NathanAzvdo.AcadPlanner.entities.Course;
import com.NathanAzvdo.AcadPlanner.services.CourseService; // Importe seu DTO de Erro

// Imports do Swagger/Springdoc
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/course")
@Tag(name = "Cursos (Admin)", description = "Endpoints para gerenciamento de courses")
@SecurityRequirement(name = "bearerAuth")
public class AdminCourseController {

    private final CourseService courseService;

    public AdminCourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping
    @Operation(summary = "Registra um novo course", description = "Cria um novo course no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso registrado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseResponse.class)) }),

            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: Nome do course é obrigatório) (BusinessException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "409", description = "Já existe um course com este name (FieldAlreadyExistsException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<CourseResponse> save(@RequestBody CourseRequest courseRequest) {
        Course toCourse = CourseMapper.toEntity(courseRequest);
        Course savedCourse = courseService.save(toCourse);
        return ResponseEntity.ok(CourseMapper.toResponse(savedCourse));
    }

    @GetMapping
    @Operation(summary = "Lista todos os courses", description = "Retorna uma lista de todos os courses cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de courses recuperada (pode ser uma lista vazia)",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CourseResponse.class))) }),

            // ATUALIZADO (se você mapeou EmptyListException para 404)
            @ApiResponse(responseCode = "404", description = "Nenhum course encontrado (EmptyListException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(courseService.listAll());
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um course", description = "Exclui um course permanentemente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso deletado com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(example = "Curso deletado com sucesso!")) }),

            // ATUALIZADO (baseado no ControllerAdvice)
            @ApiResponse(responseCode = "404", description = "Curso não encontrado (InvalidIdException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<?> deleteById(
            @Parameter(description = "ID do course name ser excluído", example = "1")
            @PathVariable Long id){
        Course course = courseService.findById(id);
        courseService.deleteById(id);
        return ResponseEntity.ok("Curso deletado com sucesso!");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza um course", description = "Atualiza os dados de um course existente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseResponse.class)) }),

            // ATUALIZADO (baseado no ControllerAdvice)
            @ApiResponse(responseCode = "404", description = "Curso não encontrado para atualização (InvalidIdException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<CourseResponse> update(
            @RequestBody CourseRequest courseRequest,
            @Parameter(description = "ID do course name ser atualizado", example = "1")
            @PathVariable Long id) {

        Course newCourse = CourseMapper.toEntity(courseRequest);
        Course updatedCourse = courseService.update(newCourse, id);
        return ResponseEntity.ok().body(CourseMapper.toResponse(updatedCourse));
    }
}