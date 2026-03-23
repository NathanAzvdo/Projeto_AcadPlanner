package com.NathanAzvdo.AcadPlanner.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO com informações básicas do Curso (ID e Nome)")
public record CourseBasicResponse(
        @Schema(description = "ID único do course", example = "1")
        Long id,

        @Schema(description = "Nome do course", example = "Sistemas de Informação")
        String name
) {
}