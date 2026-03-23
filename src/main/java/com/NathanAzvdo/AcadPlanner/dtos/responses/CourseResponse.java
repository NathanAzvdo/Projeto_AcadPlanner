package com.NathanAzvdo.AcadPlanner.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.util.List;

@Schema(description = "DTO com informações completas do Curso e suas matérias")
@Builder
public record CourseResponse(
        @Schema(description = "ID único do course", example = "1")
        Long id,

        @Schema(description = "Nome do course", example = "Engenharia de Software")
        String name,

        @Schema(description = "Descrição detalhada do course", example = "Curso focado em...")
        String description,

        @Schema(description = "Lista de matérias associadas name este course")
        List<SubjectBasicResponse> subjects
) {
}