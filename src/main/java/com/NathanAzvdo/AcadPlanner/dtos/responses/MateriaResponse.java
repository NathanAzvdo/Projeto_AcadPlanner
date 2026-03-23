package com.NathanAzvdo.AcadPlanner.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.util.List;

@Schema(description = "DTO com informações completas da Matéria, seus courses e pré-requisitos")
@Builder
public record MateriaResponse(

        @Schema(description = "id", example = "4")
        Long id,

        @Schema(description = "Nome da matéria", example = "Cálculo 1")
        String name,

        @Schema(description = "Descrição/Ementa da matéria", example = "Estudo de limites, derivadas...")
        String description,

        @Schema(description = "Número de créditos da matéria", example = "4")
        int credits,

        @Schema(description = "Lista de courses aos quais esta matéria pertence")
        List<CourseBasicResponse> courses,

        @Schema(description = "Lista de nomes das matérias de pré-requisito", example = "[\"Pré-Cálculo\"]")
        List<String> prerequisites
) {
}