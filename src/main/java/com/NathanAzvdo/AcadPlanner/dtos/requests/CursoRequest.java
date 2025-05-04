package com.NathanAzvdo.AcadPlanner.dtos.requests;

import java.util.List;

public record CursoRequest(Long id, String nome, String descricao, List<UserRequest> users, List<MateriaRequest> materias) {
}
