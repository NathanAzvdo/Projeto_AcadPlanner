package com.NathanAzvdo.AcadPlanner.dtos.responses;

import com.NathanAzvdo.AcadPlanner.entities.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "DTO de resposta com dados completos do usuário (Visão Admin)")
@Builder
public record UserAdminResponse(
        @Schema(description = "ID único do usuário", example = "1")
        Long id,

        @Schema(description = "Nome do usuário", example = "Jane Doe")
        String nome,

        @Schema(description = "Email do usuário (login)", example = "jane.doe@email.com")
        String email,

        @Schema(description = "Nível de permissão do usuário", example = "USER")
        UserRole role,

        @Schema(description = "Curso ao qual o usuário está matriculado")
        CursoBasicoResponse curso
) {
}