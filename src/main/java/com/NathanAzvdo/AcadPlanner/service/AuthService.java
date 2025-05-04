package com.NathanAzvdo.AcadPlanner.service;

import com.NathanAzvdo.AcadPlanner.config.TokenService;
import com.NathanAzvdo.AcadPlanner.entity.User;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyFieldException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidCredentialsException;
import com.NathanAzvdo.AcadPlanner.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements UserDetailsService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository,@Lazy AuthenticationManager authenticationManager){
        this.repository = repository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }

    public String login(User user){
        if(user.getEmail() == null || user.getSenha() == null){
            throw new EmptyFieldException("Email ou senha não informados");
        }
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getSenha());
            var authentication = authenticationManager.authenticate(usernamePassword);
            return new TokenService().generateToken((User) authentication.getPrincipal());
        }catch(BadCredentialsException e){
            throw new InvalidCredentialsException("Email/senha inválidos");
        }
    }


}
