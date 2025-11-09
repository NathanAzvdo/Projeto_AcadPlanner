package com.NathanAzvdo.AcadPlanner.dtos.mappers;

import com.NathanAzvdo.AcadPlanner.dtos.requests.MateriaRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CursoBasicoResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaBasicaResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entities.Curso;
import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.entities.MateriasConcluidas;
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
                .id(materia.getId())
                .nome(materia.getNome())
                .descricao(materia.getDescricao())
                .creditos(materia.getCreditos())
                .cursos(materia.getCursos().stream()
                        .map(c -> new CursoBasicoResponse(c.getId(), c.getNome()))
                        .collect(Collectors.toList()))
                .preRequisitos(materia.getPreRequisitos().stream()
                        .map(Materia::getNome)
                        .collect(Collectors.toList()))
                .build();
    }

    public static MateriaBasicaResponse toMateriaBasicaResponse(MateriasConcluidas materia) {
        return new MateriaBasicaResponse(
                materia.getMateria().getId(),
                materia.getMateria().getNome(),
                materia.getMateria().getDescricao(),
                materia.getMateria().getCreditos()
        );
    }

}