package com.NathanAzvdo.AcadPlanner.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(description = "DTO de resposta padrão para erros da API")
public record ErrorResponse(
        @Schema(description = "Código de status HTTP", example = "404")
        int status,

        @Schema(description = "Frase do status HTTP", example = "Not Found")
        String error,

        @Schema(description = "Mensagem de erro detalhada", example = "Curso não encontrado para o ID: 99")
        String message,

        @Schema(description = "Path da API que originou o erro", example = "/admin/curso/99")
        String path,

        @Schema(description = "Timestamp de quando o erro ocorreu (UTC)")
        Instant timestamp
) {}