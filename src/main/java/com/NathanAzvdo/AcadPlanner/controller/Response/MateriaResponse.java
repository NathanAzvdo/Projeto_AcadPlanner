package com.NathanAzvdo.AcadPlanner.controller.Response;

import com.NathanAzvdo.AcadPlanner.entity.Curso;
import com.NathanAzvdo.AcadPlanner.entity.Materia;
import lombok.Builder;

import java.util.List;

@Builder
public record MateriaResponse(String nome, String descricao, int creditos, List<Curso> curso, List<Materia> preRequisito) {
}
