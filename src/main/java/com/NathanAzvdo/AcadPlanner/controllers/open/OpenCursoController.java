package com.NathanAzvdo.AcadPlanner.controllers.open;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.CursoMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CursoBasicoResponse;
import com.NathanAzvdo.AcadPlanner.services.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@Tag(
        name = "Cursos (Acesso Público)",
        description = "Endpoints abertos para consulta de cursos disponíveis na plataforma."
)
public class OpenCursoController {

    private final CursoService cursoService;

    public OpenCursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @Operation(
            summary = "Listar cursos disponíveis",
            description = "Retorna uma lista com todos os cursos cadastrados no sistema. " +
                    "Este endpoint é público e não requer autenticação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista retornada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CursoBasicoResponse.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao buscar os cursos",
                            content = @Content
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<CursoBasicoResponse>> getCursos() {
        List<CursoBasicoResponse> cursos = cursoService.listAll().stream()
                .map(CursoMapper::toBasicoResponse)
                .toList();

        return ResponseEntity.ok(cursos);
    }
}
