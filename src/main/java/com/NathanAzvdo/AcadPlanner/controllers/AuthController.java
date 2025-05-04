package com.NathanAzvdo.AcadPlanner.controllers;


import com.NathanAzvdo.AcadPlanner.dtos.mappers.UserMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.UserRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.LoginResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.UserResponse;
import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.services.AuthService;
import com.NathanAzvdo.AcadPlanner.services.UserService;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid UserRequest userRequest){
        User data = UserMapper.toEntityBasic(userRequest);
        String token = authService.login(data);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequest userRequest) {
        User user = UserMapper.toEntityBasic(userRequest);
        UserResponse userResponse=  UserMapper.toResponse(userService.register(user));
        return ResponseEntity.ok().body(userResponse);
    }

}
