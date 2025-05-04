package com.NathanAzvdo.AcadPlanner.dtos.requests;

import java.util.List;

public record MateriaRequest(String nome, String descricao, int creditos, List<CursoRequest> curso, List<MateriaRequest> preRequisito) {
}
