package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyFieldException;
import com.NathanAzvdo.AcadPlanner.exceptions.FieldAlreadyExistsException;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User register(User user) {

            if (user.getName() == null || user.getEmail() == null ||
                    user.getPassword() == null || user.getCourse().getId() == null) {
                throw new EmptyFieldException("Preencha todos os campos!");
            }


            if (repository.findByEmail(user.getEmail()).isPresent()) {
                throw new FieldAlreadyExistsException("Email já cadastrado!");
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(encryptedPassword);

            return repository.save(user);
    }

}
