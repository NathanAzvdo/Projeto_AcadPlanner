package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.Course;
import com.NathanAzvdo.AcadPlanner.exceptions.BusinessException;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyListException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public Course save(Course course) {
            validateCourse(course);
            return courseRepository.save(course);

    }

    public Optional<Object> findByNome(String nome) {
        return courseRepository.findByName(nome);
    }

    private void validateCourse(Course course) {
        if (course == null) {
            throw new BusinessException("O curso não pode ser nulo");
        }

        Optional.ofNullable(course.getName())
                .filter(nome -> !nome.trim().isEmpty())
                .filter(nome -> nome.length() <= 100)
                .orElseThrow(() -> new BusinessException("Nome do curso é obrigatório e deve ter no máximo 100 caracteres"));

        Optional.ofNullable(course.getDescription())
                .ifPresent(descricao -> {
                    if (descricao.length() > 500) {
                        throw new BusinessException("Descrição do curso deve ter no máximo 500 caracteres");
                    }
                });

        if (courseRepository.findByName(course.getName()).isPresent()) {
            throw new BusinessException("Já existe um curso com este name");
        }
    }

    public List<Course> listAll(){
            List<Course> courses = courseRepository.findAll();
            if (courses.isEmpty()) {
                throw new EmptyListException("Nenhum curso encontrado.");
            }
            return courses;

    }

    public Course findById(Long id){
            return courseRepository.findById(id)
                    .orElseThrow(() -> new InvalidIdException("Curso com ID " + id + " não encontrado"));
    }

    public Course findUserCourse(Long courseId){
            if (courseId == null) {
                throw new InvalidIdException("Curso não encontrado para o usuário");
            }
            return courseRepository.findById(courseId)
                    .orElseThrow(() -> new InvalidIdException("Curso não encontrado"));
    }

    public void deleteById(Long id){
            Optional<Course> curso = courseRepository.findById(id);
            if (curso.isEmpty()) {
                throw new InvalidIdException("Id inválido!");
            }
            courseRepository.deleteById(id);

    }

    public Course update(Course course, Long id) {
            Course courseToUpdate = courseRepository.findById(id)
                    .orElseThrow(() -> new InvalidIdException("Curso com ID " + id + " não encontrado para atualização"));

            Optional.ofNullable(course.getName()).ifPresent(courseToUpdate::setName);
            Optional.ofNullable(course.getDescription()).ifPresent(courseToUpdate::setDescription);

            Optional.ofNullable(course.getUsers())
                    .filter(users -> !users.isEmpty())
                    .ifPresent(courseToUpdate::setUsers);

            Optional.ofNullable(course.getSubjects())
                    .filter(subjects -> !subjects.isEmpty())
                    .ifPresent(courseToUpdate::setSubjects);

            return courseRepository.save(courseToUpdate);

    }
}