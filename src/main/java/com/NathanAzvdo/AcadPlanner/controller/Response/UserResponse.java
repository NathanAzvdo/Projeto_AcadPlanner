package com.NathanAzvdo.AcadPlanner.controller.Response;

import lombok.Builder;

@Builder
public record UserResponse(Long id, String nome, String email, CursoBasicoResponse curso) {
}
