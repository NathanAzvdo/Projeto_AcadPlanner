package com.NathanAzvdo.AcadPlanner.dtos.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record MateriaResponse(
        String nome,
        String descricao,
        int creditos,
        List<CursoBasicoResponse> cursos,
        List<String> preRequisitos
) {
}
