package com.NathanAzvdo.AcadPlanner.dtos.requests;

import com.NathanAzvdo.AcadPlanner.entities.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para um Admin atualizar os dados de um usuário")
public record UserAdminUpdateRequest(

        @Schema(description = "Novo nome completo do usuário", example = "Jane Doe")
        String nome,

        @Schema(description = "Novo email do usuário (deve ser único)", example = "jane.doe@email.com")
        String email,

        @Schema(description = "ID do novo curso para o qual o usuário será movido")
        CursoIDRequest curso,

        @Schema(description = "Novo nível de permissão do usuário (ADMIN ou USER)", example = "USER")
        UserRole role
) {
}