package com.NathanAzvdo.AcadPlanner.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para criar ou atualizar uma Matéria (Admin)")
public record SubjectRequest(
        @Schema(description = "Nome da matéria", example = "Cálculo 1")
        String name,

        @Schema(description = "Descrição/Ementa da matéria", example = "Estudo de limites, derivadas...")
        String description,

        @Schema(description = "Número de créditos da matéria", example = "4")
        int credits,

        @Schema(description = "(Opcional) Lista de courses para associar à matéria")
        List<CourseRequest> courses,

        @Schema(description = "(Opcional) Lista de matérias que são pré-requisitos")
        List<SubjectRequest> prerequisites
) {
}