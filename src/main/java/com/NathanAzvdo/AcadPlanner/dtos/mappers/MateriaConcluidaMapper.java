package com.NathanAzvdo.AcadPlanner.dtos.mappers;

import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaConcluidaIdResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaConcluidaResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.UserResponse;
import com.NathanAzvdo.AcadPlanner.entities.MateriasConcluidas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MateriaConcluidaMapper {

    private final MateriaConcluidaIdMapper materiaConcluidaIdMapper;

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
}
