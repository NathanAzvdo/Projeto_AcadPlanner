package com.NathanAzvdo.AcadPlanner.controller.Request;

import com.NathanAzvdo.AcadPlanner.controller.Response.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.controller.Response.UserResponse;

import java.util.List;

public record CursoRequest(Long id, String nome, String descricao, List<UserResponse> users, List<MateriaResponse> materias) {
}
