package com.NathanAzvdo.AcadPlanner.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para registro de um novo usuário. Usado também para login (ignorando name e course).")
public record UserRequest(
        @Schema(description = "Nome completo do usuário", example = "Nathan Azevedo")
        String name,

        @Schema(description = "Senha do usuário", example = "senhaForte123")
        String password,

        @Schema(description = "Email do usuário (usado como login)", example = "aluno@email.com")
        String email,

        @Schema(description = "ID do course ao qual o usuário está se matriculando")
        CourseRequestId course
){
}