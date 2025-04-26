package com.NathanAzvdo.AcadPlanner.controller.Response;

public record UserResponse(Long id, String nome, String email, boolean admin, CursoResponse curso) {
}
