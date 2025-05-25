package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.exceptions.BusinessException;
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
        try {
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
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

}
