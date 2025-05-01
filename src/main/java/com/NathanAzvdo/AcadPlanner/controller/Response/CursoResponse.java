package com.NathanAzvdo.AcadPlanner.controller.Response;

import lombok.Builder;

import java.util.List;

@Builder
public record CursoResponse(Long id, String nome, String descricao, List<MateriaBasicaResponse> materias) {
}
