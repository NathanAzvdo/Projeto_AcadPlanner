package com.NathanAzvdo.AcadPlanner.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta contendo o token JWT")
public record LoginResponse(
        @Schema(description = "Token de autenticação JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {
}