package com.NathanAzvdo.AcadPlanner.controller.Mapper;

import com.NathanAzvdo.AcadPlanner.controller.Request.UserRequest;
import com.NathanAzvdo.AcadPlanner.controller.Response.CursoBasicoResponse;

import com.NathanAzvdo.AcadPlanner.controller.Response.UserResponse;
import com.NathanAzvdo.AcadPlanner.entity.Curso;
import com.NathanAzvdo.AcadPlanner.entity.User;
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