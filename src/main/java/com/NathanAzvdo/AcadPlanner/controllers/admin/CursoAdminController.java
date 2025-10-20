package com.NathanAzvdo.AcadPlanner.controllers.admin;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.CursoMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.CursoRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CursoResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import com.NathanAzvdo.AcadPlanner.entities.Curso;
import com.NathanAzvdo.AcadPlanner.services.CursoService; // Importe seu DTO de Erro

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
@RequestMapping("/admin/curso")
@Tag(name = "Cursos (Admin)", description = "Endpoints para gerenciamento de cursos")
@SecurityRequirement(name = "bearerAuth")
public class CursoAdminController {

    private final CursoService cursoService;

    public CursoAdminController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    @PostMapping
    @Operation(summary = "Registra um novo curso", description = "Cria um novo curso no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso registrado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CursoResponse.class)) }),

            // ATUALIZADO (baseado no ControllerAdvice)
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: Nome do curso é obrigatório) (BusinessException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "409", description = "Já existe um curso com este nome (FieldAlreadyExistsException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<CursoResponse> save(@RequestBody CursoRequest cursoRequest) {
        Curso toCurso = CursoMapper.toEntity(cursoRequest);
        Curso savedCurso = cursoService.save(toCurso);
        return ResponseEntity.ok(CursoMapper.toResponse(savedCurso));
    }

    @GetMapping
    @Operation(summary = "Lista todos os cursos", description = "Retorna uma lista de todos os cursos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cursos recuperada (pode ser uma lista vazia)",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CursoResponse.class))) }),

            // ATUALIZADO (se você mapeou EmptyListException para 404)
            @ApiResponse(responseCode = "404", description = "Nenhum curso encontrado (EmptyListException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(cursoService.listAll());
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um curso", description = "Exclui um curso permanentemente pelo seu ID.")
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
            @Parameter(description = "ID do curso a ser excluído", example = "1")
            @PathVariable Long id){
        Curso curso = cursoService.findById(id);
        cursoService.deleteById(id);
        return ResponseEntity.ok("Curso deletado com sucesso!");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza um curso", description = "Atualiza os dados de um curso existente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CursoResponse.class)) }),

            // ATUALIZADO (baseado no ControllerAdvice)
            @ApiResponse(responseCode = "404", description = "Curso não encontrado para atualização (InvalidIdException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<CursoResponse> update(
            @RequestBody CursoRequest cursoRequest,
            @Parameter(description = "ID do curso a ser atualizado", example = "1")
            @PathVariable Long id) {

        Curso newCurso = CursoMapper.toEntity(cursoRequest);
        Curso updatedCurso = cursoService.update(newCurso, id);
        return ResponseEntity.ok().body(CursoMapper.toResponse(updatedCurso));
    }
}