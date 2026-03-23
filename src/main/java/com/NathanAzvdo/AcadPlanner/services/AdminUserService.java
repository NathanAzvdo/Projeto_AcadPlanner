package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.Course;
import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.exceptions.FieldAlreadyExistsException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.CourseRepository;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public AdminUserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public User updateUser(Long id, User userUpdateData) {

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Usuário não encontrado para o ID: " + id));

        Optional.ofNullable(userUpdateData.getName()).ifPresent(userToUpdate::setName);

        if (userUpdateData.getEmail() != null && !Objects.equals(userToUpdate.getEmail(), userUpdateData.getEmail())) {

            if (userRepository.findByEmail(userUpdateData.getEmail()).isPresent()) {
                throw new FieldAlreadyExistsException("O email '" + userUpdateData.getEmail() + "' já está em uso.");
            }
            userToUpdate.setEmail(userUpdateData.getEmail());
        }

        if (userUpdateData.getCourse() != null && userUpdateData.getCourse().getId() != null) {

            Course newCourse = courseRepository.findById(userUpdateData.getCourse().getId())
                    .orElseThrow(() -> new InvalidIdException("Curso não encontrado para o ID: " + userUpdateData.getCourse().getId()));

            userToUpdate.setCourse(newCourse);
        }

        Optional.ofNullable(userUpdateData.getRole()).ifPresent(userToUpdate::setRole);

        return userRepository.save(userToUpdate);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}