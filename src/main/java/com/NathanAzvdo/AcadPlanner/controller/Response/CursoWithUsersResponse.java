package com.NathanAzvdo.AcadPlanner.controller.Response;

import java.util.List;

public record CursoWithUsersResponse(Long id, String nome, String descricao, List<UserResponse> users, List<MateriaResponse> materias) {
}
