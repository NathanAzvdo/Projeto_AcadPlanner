package com.NathanAzvdo.AcadPlanner.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.util.List;

@Schema(description = "DTO com informações completas do Curso e suas matérias")
@Builder
public record CursoResponse(
        @Schema(description = "ID único do curso", example = "1")
        Long id,

        @Schema(description = "Nome do curso", example = "Engenharia de Software")
        String nome,

        @Schema(description = "Descrição detalhada do curso", example = "Curso focado em...")
        String descricao,

        @Schema(description = "Lista de matérias associadas a este curso")
        List<MateriaBasicaResponse> materias
) {
}