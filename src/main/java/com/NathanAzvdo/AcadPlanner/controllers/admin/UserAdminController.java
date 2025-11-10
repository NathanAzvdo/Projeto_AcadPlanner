package com.NathanAzvdo.AcadPlanner.controllers.admin;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.UserMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.UserAdminUpdateRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.UserAdminResponse;
import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.services.UserAdminService;
import com.NathanAzvdo.AcadPlanner.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "Usuários (Admin)", description = "Endpoints para gerenciamento de usuários")
@SecurityRequirement(name = "bearerAuth")
public class UserAdminController {

    private final UserAdminService userAdminService;

    public UserAdminController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário",
            description = "Atualiza os dados de um usuário (nome, email, curso, role) pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserAdminResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário ou Curso não encontrado (InvalidIdException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "O email informado já está em uso (FieldAlreadyExistsException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de negócio (BusinessException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })

    public ResponseEntity<UserAdminResponse> updateUser(
            @Parameter(description = "ID do usuário a ser atualizado", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody UserAdminUpdateRequest requestDto) {

        User userDataToUpdate = UserMapper.toEntityAdminUpdate(requestDto);
        User updatedUser = userAdminService.updateUser(id, userDataToUpdate);

        return ResponseEntity.ok(UserMapper.toAdminResponse(updatedUser));
    }


    @GetMapping
    @Operation(summary = "Lista todos os usuários",
            description = "Retorna uma lista com os dados de todos os usuários cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserAdminResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado (EmptyListException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<UserAdminResponse>> findAllUsers() {
        List<User> users = userAdminService.findAll();

        List<UserAdminResponse> response = users.stream()
                .map(UserMapper::toAdminResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}