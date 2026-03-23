package com.NathanAzvdo.AcadPlanner.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO wrapper para um ID de Curso")
public record CourseRequestId(
        @Schema(description = "ID do course", example = "1")
        Long id
) {
}