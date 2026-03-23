package com.NathanAzvdo.AcadPlanner.controllers.user;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.CourseMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CourseResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import com.NathanAzvdo.AcadPlanner.entities.Course;
import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
@Tag(name = "Cursos (Usuário)", description = "Endpoints de consulta de courses para usuários logados")
@SecurityRequirement(name = "bearerAuth")
public class CursoController {

    private final CourseService courseService;

    public CursoController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/meu-course")
    @Operation(summary = "Busca o course do usuário logado",
            description = "Retorna os detalhes do course ao qual o usuário autenticado está matriculado, com base no token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido ou expirado)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado para este usuário (InvalidIdException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Erro de negócio genérico (BusinessException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<CourseResponse> findUserCourse(@AuthenticationPrincipal User user) {
        Course course = courseService.findUserCourse(user.getCourse().getId());
        return ResponseEntity.ok(CourseMapper.toResponse(course));
    }

}