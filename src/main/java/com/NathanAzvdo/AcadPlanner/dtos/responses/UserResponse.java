package com.NathanAzvdo.AcadPlanner.dtos.responses;

import lombok.Builder;

@Builder
public record UserResponse(Long id, String nome, String email, CursoBasicoResponse curso) {
}
