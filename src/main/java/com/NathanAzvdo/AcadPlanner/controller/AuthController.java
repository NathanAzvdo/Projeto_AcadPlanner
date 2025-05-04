package com.NathanAzvdo.AcadPlanner.controller;


import com.NathanAzvdo.AcadPlanner.config.TokenService;
import com.NathanAzvdo.AcadPlanner.controller.Mapper.UserMapper;
import com.NathanAzvdo.AcadPlanner.controller.Request.UserRequest;
import com.NathanAzvdo.AcadPlanner.controller.Response.LoginDTO;
import com.NathanAzvdo.AcadPlanner.controller.Response.UserResponse;
import com.NathanAzvdo.AcadPlanner.entity.User;
import com.NathanAzvdo.AcadPlanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserRequest userRequest){
        User data = UserMapper.toEntityBasic(userRequest);
        if(data.getEmail() == null || data.getSenha() == null) return ResponseEntity.badRequest().body("Email ou senha não informados");
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());
        var authentication = authenticationManager.authenticate(usernamePassword);
        var token = new TokenService().generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new LoginDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequest userRequest) {
        if (userRequest.nome() == null || userRequest.email() == null ||
                userRequest.senha() == null || userRequest.curso().id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Preencha todos os campos!");
        }

        User data = UserMapper.toEntityBasic(userRequest);
        if (userRepository.findByEmail(data.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email já cadastrado!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getSenha());
        data.setSenha(encryptedPassword);

        User newUserSaved = userRepository.save(data);
        return ResponseEntity.ok().body(UserMapper.toResponse(newUserSaved));
    }

}
