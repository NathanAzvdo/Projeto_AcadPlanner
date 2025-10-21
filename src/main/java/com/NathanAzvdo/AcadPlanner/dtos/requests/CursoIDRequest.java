package com.NathanAzvdo.AcadPlanner.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO wrapper para um ID de Curso")
public record CursoIDRequest(
        @Schema(description = "ID do curso", example = "1")
        Long id
) {
}