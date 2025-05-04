package com.NathanAzvdo.AcadPlanner.controller;


import com.NathanAzvdo.AcadPlanner.config.TokenService;
import com.NathanAzvdo.AcadPlanner.controller.Mapper.UserMapper;
import com.NathanAzvdo.AcadPlanner.controller.Request.UserRequest;
import com.NathanAzvdo.AcadPlanner.controller.Response.LoginDTO;
import com.NathanAzvdo.AcadPlanner.controller.Response.UserResponse;
import com.NathanAzvdo.AcadPlanner.entity.User;
import com.NathanAzvdo.AcadPlanner.repository.UserRepository;
import com.NathanAzvdo.AcadPlanner.service.UserService;
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

    @Autowired
    private UserService userService;


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
        User user = UserMapper.toEntityBasic(userRequest);
        UserResponse userResponse=  UserMapper.toResponse(userService.register(user));
        return ResponseEntity.ok().body(userResponse);
    }

}
