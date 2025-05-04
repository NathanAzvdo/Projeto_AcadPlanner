package com.NathanAzvdo.AcadPlanner.dtos.mappers;

import com.NathanAzvdo.AcadPlanner.dtos.requests.CursoRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CursoBasicoResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CursoResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaBasicaResponse;
import com.NathanAzvdo.AcadPlanner.entities.Curso;

import java.util.stream.Collectors;

public class CursoMapper {

    public static CursoResponse toResponse(Curso curso) {
        if (curso == null) return null;

        return new CursoResponse(
                curso.getId(),
                curso.getNome(),
                curso.getDescricao(),
                curso.getMaterias() != null ?
                        curso.getMaterias().stream()
                                .map(m -> new MateriaBasicaResponse(m.getNome(), m.getDescricao(), m.getCreditos()))
                                .collect(Collectors.toList())
                        : null
        );
    }

    public static Curso toEntity(CursoRequest request) {
        if (request == null) return null;

        Curso curso = new Curso();
        curso.setId(request.id());
        curso.setNome(request.nome());
        curso.setDescricao(request.descricao());

        if (request.users() != null) {
            curso.setUsuarios(request.users().stream()
                    .map(UserMapper::toEntityBasic)
                    .collect(Collectors.toList()));
        }

        if (request.materias() != null) {
            curso.setMaterias(request.materias().stream()
                    .map(MateriaMapper::toMateria)
                    .collect(Collectors.toList()));
        }

        return curso;
    }


    public static Curso toEntityBasic(Long id) {
        if (id == null) return null;
        Curso curso = new Curso();
        curso.setId(id);
        return curso;
    }

    public static CursoBasicoResponse toBasicoResponse(Curso curso) {
        if (curso == null) return null;

        return new CursoBasicoResponse(
                curso.getId()
        );
    }
}
