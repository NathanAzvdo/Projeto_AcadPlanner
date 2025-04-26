package com.NathanAzvdo.AcadPlanner.controller.Mapper;

import com.NathanAzvdo.AcadPlanner.controller.Request.MateriaRequest;
import com.NathanAzvdo.AcadPlanner.controller.Response.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entity.Materia;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MateriaMapper {

    public static Materia toMateria(MateriaRequest materiaRequest){
        return Materia.builder()
                .nome(materiaRequest.nome())
                .creditos(materiaRequest.creditos())
                .cursos(materiaRequest.curso())
                .descricao(materiaRequest.descricao())
                .preRequisitos(materiaRequest.preRequisito())
                .build();
    }

    public static MateriaResponse toMateriaResponse(Materia materia){
        return MateriaResponse.builder()
                .nome(materia.getNome())
                .creditos(materia.getCreditos())
                .curso(materia.getCursos())
                .descricao(materia.getDescricao())
                .preRequisito(materia.getPreRequisitos())
                .build();
    }
}
