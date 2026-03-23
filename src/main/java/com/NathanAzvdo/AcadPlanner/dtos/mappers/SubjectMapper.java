package com.NathanAzvdo.AcadPlanner.dtos.mappers;

import com.NathanAzvdo.AcadPlanner.dtos.requests.SubjectRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.CourseBasicResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.SubjectBasicResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entities.Course;
import com.NathanAzvdo.AcadPlanner.entities.Subject;
import com.NathanAzvdo.AcadPlanner.entities.CompletedSubject;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class SubjectMapper {

    public static Subject toSubject(SubjectRequest subjectRequest) {
        List<Course> courseEntities = subjectRequest.courses()
                .stream()
                .map(CourseMapper::toEntity)
                .collect(Collectors.toList());

        List<Subject> subjectEntities = subjectRequest.prerequisites()
                .stream()
                .map(SubjectMapper::toSubject)
                .collect(Collectors.toList());

        return Subject.builder()
                .name(subjectRequest.name())
                .credits(subjectRequest.credits())
                .courses(courseEntities)
                .description(subjectRequest.description())
                .prerequisites(subjectEntities)
                .build();
    }

    public static MateriaResponse toMateriaResponse(Subject subject) {
        return MateriaResponse.builder()
                .id(subject.getId())
                .name(subject.getName())
                .description(subject.getDescription())
                .credits(subject.getCredits())
                .courses(subject.getCourses().stream()
                        .map(c -> new CourseBasicResponse(c.getId(), c.getName()))
                        .collect(Collectors.toList()))
                .prerequisites(subject.getPrerequisites().stream()
                        .map(Subject::getName)
                        .collect(Collectors.toList()))
                .build();
    }

    public static SubjectBasicResponse toMateriaBasicaResponse(CompletedSubject materia) {
        return new SubjectBasicResponse(
                materia.getSubject().getId(),
                materia.getSubject().getName(),
                materia.getSubject().getDescription(),
                materia.getSubject().getCredits()
        );
    }

}