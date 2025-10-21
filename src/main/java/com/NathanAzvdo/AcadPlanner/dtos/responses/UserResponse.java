package com.NathanAzvdo.AcadPlanner.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "DTO com dados públicos do usuário após registro")
@Builder
public record UserResponse(
        @Schema(description = "ID único do usuário", example = "1")
        Long id,

        @Schema(description = "Nome do usuário", example = "Nathan Azevedo")
        String nome,

        @Schema(description = "Email do usuário", example = "aluno@email.com")
        String email,

        @Schema(description = "Curso ao qual o usuário está matriculado")
        CursoBasicoResponse curso
) {
}