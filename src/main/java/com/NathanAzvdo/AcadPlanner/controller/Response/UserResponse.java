package com.NathanAzvdo.AcadPlanner.controller.Response;

import com.NathanAzvdo.AcadPlanner.controller.Response.CursoResponse;

public record UserResponse(Long id, String nome, String email, boolean admin, CursoResponse curso) {
}
