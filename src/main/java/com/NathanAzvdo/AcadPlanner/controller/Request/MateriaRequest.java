package com.NathanAzvdo.AcadPlanner.controller.Request;

import com.NathanAzvdo.AcadPlanner.entity.Curso;
import com.NathanAzvdo.AcadPlanner.entity.Materia;
import lombok.Builder;

import java.util.List;

public record MateriaRequest(String nome, String descricao, int creditos, List<Curso> curso, List<Materia> preRequisito) {
}
