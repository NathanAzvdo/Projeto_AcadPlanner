package com.NathanAzvdo.AcadPlanner.dtos.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record CursoResponse(Long id, String nome, String descricao, List<MateriaBasicaResponse> materias) {
}
