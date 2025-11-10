package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.Curso;
import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.exceptions.FieldAlreadyExistsException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.CursoRepository;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserAdminService {

    private final UserRepository userRepository;
    private final CursoRepository cursoRepository;

    public UserAdminService(UserRepository userRepository, CursoRepository cursoRepository) {
        this.userRepository = userRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public User updateUser(Long id, User userUpdateData) {

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Usuário não encontrado para o ID: " + id));

        Optional.ofNullable(userUpdateData.getNome()).ifPresent(userToUpdate::setNome);

        if (userUpdateData.getEmail() != null && !Objects.equals(userToUpdate.getEmail(), userUpdateData.getEmail())) {

            if (userRepository.findByEmail(userUpdateData.getEmail()).isPresent()) {
                throw new FieldAlreadyExistsException("O email '" + userUpdateData.getEmail() + "' já está em uso.");
            }
            userToUpdate.setEmail(userUpdateData.getEmail());
        }

        if (userUpdateData.getCurso() != null && userUpdateData.getCurso().getId() != null) {

            Curso novoCurso = cursoRepository.findById(userUpdateData.getCurso().getId())
                    .orElseThrow(() -> new InvalidIdException("Curso não encontrado para o ID: " + userUpdateData.getCurso().getId()));

            userToUpdate.setCurso(novoCurso);
        }

        Optional.ofNullable(userUpdateData.getRole()).ifPresent(userToUpdate::setRole);

        return userRepository.save(userToUpdate);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}