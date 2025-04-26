package com.NathanAzvdo.AcadPlanner.controller.Response;

import java.util.List;

public record CursoResponse(Long id, String nome, String descricao, List<MateriaResponse> materias) {
}
