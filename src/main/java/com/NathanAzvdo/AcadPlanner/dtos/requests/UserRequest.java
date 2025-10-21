package com.NathanAzvdo.AcadPlanner.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para registro de um novo usuário. Usado também para login (ignorando nome e curso).")
public record UserRequest(
        @Schema(description = "Nome completo do usuário", example = "Nathan Azevedo")
        String nome,

        @Schema(description = "Senha do usuário", example = "senhaForte123")
        String senha,

        @Schema(description = "Email do usuário (usado como login)", example = "aluno@email.com")
        String email,

        @Schema(description = "ID do curso ao qual o usuário está se matriculando")
        CursoIDRequest curso
){
}