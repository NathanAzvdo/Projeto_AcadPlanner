package com.NathanAzvdo.AcadPlanner.dtos.responses;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.time.LocalDate;

public record MateriaEmAndamentoRespose(Long usuarioId, Long materiaId,
                                        LocalDate dataInicio, LocalDate previsaoConclusao) {
}
