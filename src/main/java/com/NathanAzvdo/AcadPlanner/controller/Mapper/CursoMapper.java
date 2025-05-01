package com.NathanAzvdo.AcadPlanner.controller.Mapper;

import com.NathanAzvdo.AcadPlanner.controller.Request.CursoRequest;
import com.NathanAzvdo.AcadPlanner.controller.Response.CursoResponse;
import com.NathanAzvdo.AcadPlanner.controller.Response.MateriaBasicaResponse;
import com.NathanAzvdo.AcadPlanner.entity.Curso;

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
}
