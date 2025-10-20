package com.NathanAzvdo.AcadPlanner.controllers.open;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.UserMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.UserRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.LoginResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.UserResponse;
import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.services.AuthService;
import com.NathanAzvdo.AcadPlanner.services.UserService;

// Imports do Swagger/Springdoc
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
// Agrupa todos os endpoints desta classe sob a tag "Autenticação" no Swagger UI
@Tag(name = "Autenticação", description = "Endpoints para login e registro de usuários")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }


    @PostMapping("/login")
    // Descreve o que o endpoint faz
    @Operation(summary = "Efetua o login do usuário",
            description = "Autentica um usuário com base no nome de usuário e senha e retorna um token JWT.")
    // Descreve as possíveis respostas
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: campos nulos ou mal formatados)",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Credenciais inválidas (usuário ou senha incorretos)",
                    content = @Content)
    })
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid UserRequest userRequest){
        User data = UserMapper.toEntityBasic(userRequest);
        String token = authService.login(data);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Registra um novo usuário",
            description = "Cria uma nova conta de usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: usuário já existe ou dados inválidos)",
                    content = @Content)
    })
    public ResponseEntity<?> register(@RequestBody @Valid UserRequest userRequest) {
        User user = UserMapper.toEntityBasic(userRequest);
        UserResponse userResponse=  UserMapper.toResponse(userService.register(user));
        return ResponseEntity.ok().body(userResponse);
    }
}