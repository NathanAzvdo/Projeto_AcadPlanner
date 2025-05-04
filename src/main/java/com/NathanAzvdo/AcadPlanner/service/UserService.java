package com.NathanAzvdo.AcadPlanner.service;

import com.NathanAzvdo.AcadPlanner.entity.User;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyFieldException;
import com.NathanAzvdo.AcadPlanner.exceptions.FieldAlreadyExistsException;
import com.NathanAzvdo.AcadPlanner.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User register(User user) {
        if (user.getNome() == null || user.getEmail() == null ||
                user.getSenha() == null || user.getCurso().getId() == null) {
            throw new EmptyFieldException("Preencha todos os campos!");
        }

        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new FieldAlreadyExistsException("Email já cadastrado!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getSenha());
        user.setSenha(encryptedPassword);

        return repository.save(user);
    }

}
