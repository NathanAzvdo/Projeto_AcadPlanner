package com.NathanAzvdo.AcadPlanner.dtos.mappers;

import com.NathanAzvdo.AcadPlanner.dtos.requests.UserRequest;

import com.NathanAzvdo.AcadPlanner.dtos.responses.UserResponse;
import com.NathanAzvdo.AcadPlanner.entities.Curso;
import com.NathanAzvdo.AcadPlanner.entities.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static User toEntityBasic(UserRequest request) {
        return User.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(request.senha())
                .curso(request.curso() != null ?
                        Curso.builder().id(request.curso().id()).build() :
                        null)
                .build();
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .curso(user.getCurso() != null ? CursoMapper.toBasicoResponse(user.getCurso()) : null)
                .build();
    }
}