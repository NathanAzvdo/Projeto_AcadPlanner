package com.NathanAzvdo.AcadPlanner.dtos.requests;

import java.util.List;

public record CursoRequest(String nome, String descricao, List<UserRequest> users, List<MateriaRequest> materias) {
}
