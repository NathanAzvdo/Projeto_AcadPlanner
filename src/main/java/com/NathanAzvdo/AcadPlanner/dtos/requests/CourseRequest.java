package com.NathanAzvdo.AcadPlanner.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO para criar ou atualizar um Curso (Admin)")
public record CourseRequest(
        @Schema(description = "Nome do course", example = "Engenharia de Software")
        String name,

        @Schema(description = "Descrição detalhada do course", example = "Curso focado em...")
        String description,

        @Schema(description = "(Opcional) Lista de usuários para associar ao course")
        List<UserRequest> users,

        @Schema(description = "(Opcional) Lista de matérias para associar ao course")
        List<SubjectRequest> subjects
) {
}