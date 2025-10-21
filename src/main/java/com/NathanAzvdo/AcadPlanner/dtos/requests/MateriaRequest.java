package com.NathanAzvdo.AcadPlanner.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para criar ou atualizar uma Matéria (Admin)")
public record MateriaRequest(
        @Schema(description = "Nome da matéria", example = "Cálculo 1")
        String nome,

        @Schema(description = "Descrição/Ementa da matéria", example = "Estudo de limites, derivadas...")
        String descricao,

        @Schema(description = "Número de créditos da matéria", example = "4")
        int creditos,

        @Schema(description = "(Opcional) Lista de cursos para associar à matéria")
        List<CursoRequest> curso,

        @Schema(description = "(Opcional) Lista de matérias que são pré-requisitos")
        List<MateriaRequest> preRequisito
) {
}