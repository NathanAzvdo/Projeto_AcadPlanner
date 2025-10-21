package com.NathanAzvdo.AcadPlanner.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para criar ou atualizar um Curso (Admin)")
public record CursoRequest(
        @Schema(description = "Nome do curso", example = "Engenharia de Software")
        String nome,

        @Schema(description = "Descrição detalhada do curso", example = "Curso focado em...")
        String descricao,

        @Schema(description = "(Opcional) Lista de usuários para associar ao curso")
        List<UserRequest> users,

        @Schema(description = "(Opcional) Lista de matérias para associar ao curso")
        List<MateriaRequest> materias
) {
}