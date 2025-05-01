package com.NathanAzvdo.AcadPlanner.controller.Mapper;

import com.NathanAzvdo.AcadPlanner.controller.Request.CursoRequest;
import com.NathanAzvdo.AcadPlanner.controller.Request.MateriaRequest;
import com.NathanAzvdo.AcadPlanner.controller.Response.CursoBasicoResponse;
import com.NathanAzvdo.AcadPlanner.controller.Response.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entity.Curso;
import com.NathanAzvdo.AcadPlanner.entity.Materia;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MateriaMapper {

    public static Materia toMateria(MateriaRequest materiaRequest) {
        List<Curso> cursoEntities = materiaRequest.curso()
                .stream()
                .map(CursoMapper::toEntity)
                .collect(Collectors.toList());

        List<Materia> materiaEntities= materiaRequest.preRequisito()
                .stream()
                .map(MateriaMapper::toMateria)
                .collect(Collectors.toList());

        return Materia.builder()
                .nome(materiaRequest.nome())
                .creditos(materiaRequest.creditos())
                .cursos(cursoEntities)
                .descricao(materiaRequest.descricao())
                .preRequisitos(materiaEntities)
                .build();
    }

    public static MateriaResponse toMateriaResponse(Materia materia) {
        return MateriaResponse.builder()
                .nome(materia.getNome())
                .descricao(materia.getDescricao())
                .creditos(materia.getCreditos())
                .cursos(materia.getCursos().stream()
                        .map(c -> new CursoBasicoResponse(c.getId(), c.getNome()))
                        .collect(Collectors.toList()))
                .preRequisitos(materia.getPreRequisitos().stream()
                        .map(Materia::getNome) // ou ID, conforme quiser
                        .collect(Collectors.toList()))
                .build();
    }

}
