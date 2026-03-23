package com.NathanAzvdo.AcadPlanner.dtos.requests;

import com.NathanAzvdo.AcadPlanner.entities.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para um Admin atualizar os dados de um usuário")
public record UserAdminUpdateRequest(

        @Schema(description = "Novo name completo do usuário", example = "Jane Doe")
        String name,

        @Schema(description = "Novo email do usuário (deve ser único)", example = "jane.doe@email.com")
        String email,

        @Schema(description = "ID do novo course para o qual o usuário será movido")
        CourseRequestId course,

        @Schema(description = "Novo nível de permissão do usuário (ADMIN ou USER)", example = "USER")
        UserRole role
) {
}