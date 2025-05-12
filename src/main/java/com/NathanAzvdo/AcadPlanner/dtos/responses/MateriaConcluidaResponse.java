package com.NathanAzvdo.AcadPlanner.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record MateriaConcluidaResponse(MateriaConcluidaIdResponse materiasConcluidasId,
                                       UserResponse userResponse,
                                       MateriaResponse materiaResponse,
                                       @JsonFormat(pattern = "yyyy-MM-dd")
                                       LocalDate dataConclusao)
{

}
