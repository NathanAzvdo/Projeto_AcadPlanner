package com.NathanAzvdo.AcadPlanner.dtos.mappers;

import com.NathanAzvdo.AcadPlanner.dtos.requests.UserAdminUpdateRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.UserAdminResponse;


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

    public static User toEntityAdminUpdate(UserAdminUpdateRequest request) {
        User user = new User();
        user.setNome(request.nome());
        user.setEmail(request.email());
        user.setRole(request.role());

        if (request.curso() != null) {
            user.setCurso(Curso.builder().id(request.curso().id()).build());
        }

        return user;
    }


    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .curso(user.getCurso() != null ? CursoMapper.toBasicoResponse(user.getCurso()) : null)
                .build();
    }

    public static UserAdminResponse toAdminResponse(User user) {
        return UserAdminResponse.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .role(user.getRole()) // Incluindo o Role
                .curso(user.getCurso() != null ? CursoMapper.toBasicoResponse(user.getCurso()) : null)
                .build();
    }
}