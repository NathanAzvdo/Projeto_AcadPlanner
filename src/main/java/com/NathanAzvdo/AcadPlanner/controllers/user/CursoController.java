package com.NathanAzvdo.AcadPlanner.controllers.user;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.CursoMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CursoResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import com.NathanAzvdo.AcadPlanner.entities.Curso;
import com.NathanAzvdo.AcadPlanner.services.CursoService;

// Imports do Swagger/Springdoc
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curso")
@Tag(name = "Cursos (Usuário)", description = "Endpoints de consulta de cursos para usuários logados")
@SecurityRequirement(name = "bearerAuth") // Exige autenticação em todos os endpoints
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping("/meu-curso")
    @Operation(summary = "Busca o curso do usuário logado",
            description = "Retorna os detalhes do curso ao qual o usuário autenticado está matriculado, com base no token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CursoResponse.class)) }),

            // Baseado no ControllerAdvice (FilterException)
            @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido ou expirado)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),

            // Baseado no ControllerAdvice (InvalidIdException)
            @ApiResponse(responseCode = "404", description = "Curso não encontrado para este usuário (InvalidIdException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),

            // Baseado no ControllerAdvice (BusinessException genérica)
            @ApiResponse(responseCode = "400", description = "Erro de negócio genérico (BusinessException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<CursoResponse> findUserCourse(HttpServletRequest request) {
        Curso curso = cursoService.findUserCourse(request);
        return ResponseEntity.ok(CursoMapper.toResponse(curso));
    }
}