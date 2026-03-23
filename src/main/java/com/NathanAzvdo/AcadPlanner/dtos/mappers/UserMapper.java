package com.NathanAzvdo.AcadPlanner.dtos.mappers;

import com.NathanAzvdo.AcadPlanner.dtos.requests.UserAdminUpdateRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.UserAdminResponse;


import com.NathanAzvdo.AcadPlanner.dtos.requests.UserRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.UserResponse;
import com.NathanAzvdo.AcadPlanner.entities.Course;
import com.NathanAzvdo.AcadPlanner.entities.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static User toEntityBasic(UserRequest request) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .course(request.course() != null ?
                        Course.builder().id(request.course().id()).build() :
                        null)
                .build();
    }

    public static User toEntityAdminUpdate(UserAdminUpdateRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(request.role());

        if (request.course() != null) {
            user.setCourse(Course.builder().id(request.course().id()).build());
        }

        return user;
    }


    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .course(user.getCourse() != null ? CourseMapper.toBasicoResponse(user.getCourse()) : null)
                .build();
    }

    public static UserAdminResponse toAdminResponse(User user) {
        return UserAdminResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .course(user.getCourse() != null ? CourseMapper.toBasicoResponse(user.getCourse()) : null)
                .build();
    }
}