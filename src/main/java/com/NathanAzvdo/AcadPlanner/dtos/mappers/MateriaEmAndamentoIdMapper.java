package com.NathanAzvdo.AcadPlanner.dtos.mappers;

import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaConcluidaIdResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaEmAndamentoIdResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaEmAndamentoRespose;
import com.NathanAzvdo.AcadPlanner.entities.MateriasConcluidasId;
import com.NathanAzvdo.AcadPlanner.entities.MateriasEmAndamentoId;

public class MateriaEmAndamentoIdMapper{

    public static MateriaEmAndamentoIdResponse toResponse(MateriasEmAndamentoId materiasAndamentoId){
        return new MateriaEmAndamentoIdResponse(
                materiasAndamentoId.getUsuarioId(),
                materiasAndamentoId.getMateriaId()
        );
    }

    public static MateriasEmAndamentoId toEntity(MateriaEmAndamentoIdResponse response){
        return new MateriasEmAndamentoId(
                response.usuarioId(),
                response.materiaId()
        );
    }
}
