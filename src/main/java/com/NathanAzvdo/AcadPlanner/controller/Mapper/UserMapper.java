package com.NathanAzvdo.AcadPlanner.controller.Mapper;

import com.NathanAzvdo.AcadPlanner.controller.Request.UserRequest;
import com.NathanAzvdo.AcadPlanner.controller.Response.UserResponse;
import com.NathanAzvdo.AcadPlanner.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        if (user == null) return null;
        
        return new UserResponse(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.isAdmin(),
                CursoMapper.toResponse(user.getCurso())
        );
    }

    public static User toEntityBasic(UserRequest request) {
        if (request == null) return null;
        return User.builder()
                .id(request.id())
                .nome(request.nome())
                .email(request.email())
                .admin(request.admin())
                .curso(request.curso() != null ? CursoMapper.toEntityBasic(request.curso().id()) : null)
                .build();
    }
}
