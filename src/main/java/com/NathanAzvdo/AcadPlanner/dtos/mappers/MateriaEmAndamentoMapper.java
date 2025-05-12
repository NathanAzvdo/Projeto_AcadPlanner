package com.NathanAzvdo.AcadPlanner.dtos.mappers;

import com.NathanAzvdo.AcadPlanner.dtos.responses.*;
import com.NathanAzvdo.AcadPlanner.entities.MateriasConcluidas;
import com.NathanAzvdo.AcadPlanner.entities.MateriasEmAndamento;

public class MateriaEmAndamentoMapper {

    public static MateriaConcluidaResponse toResponse(MateriasConcluidas entity) {
        MateriaConcluidaIdResponse idResponse = new MateriaConcluidaIdResponse(
                entity.getUsuario().getId(),
                entity.getMateria().getId()
        );

        UserResponse userResponse = UserMapper.toResponse(entity.getUsuario());
        MateriaResponse materiaResponse = MateriaMapper.toMateriaResponse(entity.getMateria());

        return new MateriaConcluidaResponse(
                idResponse,
                userResponse,
                materiaResponse,
                entity.getDataConclusao()
        );
    }

    public static MateriaEmAndamentoRespose toResponse(MateriasEmAndamento entity){
        MateriaEmAndamentoIdResponse idResponse = new MateriaEmAndamentoIdResponse(
                entity.getUsuarioId(),
                entity.getMateriaId()
        );

        MateriaEmAndamentoIdResponse materiaIdResponse = new MateriaEmAndamentoIdResponse(
                entity.getMateriaId(),
                entity.getUsuarioId()
        );

        return new MateriaEmAndamentoRespose(
                materiaIdResponse.usuarioId(),
                materiaIdResponse.materiaId(),
                entity.getDataInicio(),
                entity.getPrevisaoConclusao()
        );
    }

}
