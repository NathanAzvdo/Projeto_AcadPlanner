package com.NathanAzvdo.AcadPlanner.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO com informações básicas da Matéria")
public record MateriaBasicaResponse(

        @Schema(description = "id", example = "4")
        Long id,

        @Schema(description = "Nome da matéria", example = "Cálculo 1")
        String nome,

        @Schema(description = "Descrição/Ementa da matéria", example = "Estudo de limites, derivadas...")
        String descricao,

        @Schema(description = "Número de créditos da matéria", example = "4")
        int creditos
) {}