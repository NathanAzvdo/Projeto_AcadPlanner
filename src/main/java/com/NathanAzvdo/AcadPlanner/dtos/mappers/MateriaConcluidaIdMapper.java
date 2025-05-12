package com.NathanAzvdo.AcadPlanner.dtos.mappers;


import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaConcluidaIdResponse;
import com.NathanAzvdo.AcadPlanner.entities.MateriasConcluidasId;
import org.springframework.stereotype.Component;

@Component
public class MateriaConcluidaIdMapper {

    public static MateriaConcluidaIdResponse toResponse(MateriasConcluidasId entity) {
        return new MateriaConcluidaIdResponse(
                entity.getUsuarioId(),
                entity.getMateriaId()
        );
    }

    public static MateriasConcluidasId toEntity(MateriaConcluidaIdResponse response) {
        return new MateriasConcluidasId(
                response.usuarioId(),
                response.materiaId()
        );
    }
}