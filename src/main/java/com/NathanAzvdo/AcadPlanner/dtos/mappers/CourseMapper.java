package com.NathanAzvdo.AcadPlanner.dtos.mappers;

import com.NathanAzvdo.AcadPlanner.dtos.requests.CourseRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CourseBasicResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CourseResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.SubjectBasicResponse;
import com.NathanAzvdo.AcadPlanner.entities.Course;

import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseResponse toResponse(Course course) {
        if (course == null) return null;

        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getSubjects() != null ?
                        course.getSubjects().stream()
                                .map(m -> new SubjectBasicResponse(m.getId(), m.getName(), m.getDescription(), m.getCredits()))
                                .collect(Collectors.toList())
                        : null
        );
    }

    public static Course toEntity(CourseRequest request) {
        if (request == null) return null;

        Course course = new Course();
        course.setName(request.name());
        course.setDescription(request.description());

        if (request.users() != null) {
            course.setUsers(request.users().stream()
                    .map(UserMapper::toEntityBasic)
                    .collect(Collectors.toList()));
        }

        if (request.subjects() != null) {
            course.setSubjects(request.subjects().stream()
                    .map(SubjectMapper::toSubject)
                    .collect(Collectors.toList()));
        }

        return course;
    }


    public static Course toEntityBasic(Long id) {
        if (id == null) return null;
        Course course = new Course();
        course.setId(id);
        return course;
    }

    public static CourseBasicResponse toBasicoResponse(Course course) {
        if (course == null) return null;

        return new CourseBasicResponse(
                course.getId(),
                course.getName()
        );
    }
}
