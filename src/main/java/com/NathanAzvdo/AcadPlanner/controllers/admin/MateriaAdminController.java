package com.NathanAzvdo.AcadPlanner.controllers.admin;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.MateriaRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.services.MateriaAdminService;

// Imports do Swagger/Springdoc
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

@RestController
@RequestMapping("/admin/materia")
@Tag(name = "Matérias (Admin)", description = "Endpoints para gerenciamento de matérias")
@SecurityRequirement(name = "bearerAuth") // Exige autenticação em todos os endpoints
public class MateriaAdminController {

    private final MateriaAdminService materiaService;

    public MateriaAdminController(MateriaAdminService materiaService) {
        this.materiaService = materiaService;
    }

    @PostMapping("/save")
    @Operation(summary = "Registra uma nova matéria", description = "Cria uma nova matéria no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matéria registrada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MateriaResponse.class)) }),

            // Baseado no MateriaAdminService (que lança IllegalArgumentException)
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: nome vazio, créditos <= 0) (BusinessException / IllegalArgumentException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<MateriaResponse> save(@RequestBody MateriaRequest materiaRequest) {
        Materia newMateria = MateriaMapper.toMateria(materiaRequest);
        Materia materia = materiaService.save(newMateria);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(materia));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma matéria", description = "Exclui uma matéria permanentemente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matéria deletada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(example = "Matéria deletada com sucesso!")) }),

            // Baseado no ControllerAdvice (InvalidIdException -> 404)
            @ApiResponse(responseCode = "404", description = "Matéria não encontrada (InvalidIdException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<String> delete(
            @Parameter(description = "ID da matéria a ser excluída", example = "1")
            @PathVariable Long id) {
        materiaService.deleteById(id);
        return ResponseEntity.ok().body("Matéria deletada com sucesso!");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza uma matéria", description = "Atualiza os dados de uma matéria existente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matéria atualizada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MateriaResponse.class)) }),

            // Baseado no ControllerAdvice (InvalidIdException -> 404)
            @ApiResponse(responseCode = "404", description = "Matéria não encontrada para atualização (InvalidIdException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<MateriaResponse> update(
            @RequestBody MateriaRequest materiaRequest,
            @Parameter(description = "ID da matéria a ser atualizada", example = "1")
            @PathVariable Long id) {

        Materia newMateria = MateriaMapper.toMateria(materiaRequest);
        Materia updatedMateria = materiaService.updateMateria(newMateria, id);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(updatedMateria));
    }

    @PostMapping("/{materiaId}/pre-requisito/{preRequisitoId}")
    @Operation(summary = "Adiciona um pré-requisito", description = "Define uma matéria como pré-requisito de outra.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-requisito adicionado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MateriaResponse.class)) }),

            // O service lança RuntimeException, mas a intenção é 404
            @ApiResponse(responseCode = "404", description = "Matéria ou Pré-requisito não encontrados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<MateriaResponse> addPreRequisito(
            @Parameter(description = "ID da matéria (ex: Cálculo 2)", example = "2")
            @PathVariable Long materiaId,

            @Parameter(description = "ID do pré-requisito (ex: Cálculo 1)", example = "1")
            @PathVariable Long preRequisitoId) {

        Materia updatedMateria = materiaService.addPreRequisito(materiaId, preRequisitoId);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(updatedMateria));
    }

    @DeleteMapping("/{materiaId}/pre-requisito/{preRequisitoId}")
    @Operation(summary = "Remove um pré-requisito", description = "Remove a dependência de pré-requisito de uma matéria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-requisito removido com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MateriaResponse.class)) }),

            @ApiResponse(responseCode = "404", description = "Matéria ou Pré-requisito não encontrados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<MateriaResponse> removePreRequisito(
            @Parameter(description = "ID da matéria (ex: Cálculo 2)", example = "2")
            @PathVariable Long materiaId,

            @Parameter(description = "ID do pré-requisito (ex: Cálculo 1)", example = "1")
            @PathVariable Long preRequisitoId) {

        Materia updatedMateria = materiaService.removePreRequisito(materiaId, preRequisitoId);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(updatedMateria));
    }

    @GetMapping
    @Operation(summary = "Lista todas as matérias", description = "Retorna uma lista de todas as matérias cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de matérias recuperada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MateriaResponse.class))) }),

            // Baseado no ControllerAdvice (EmptyListException -> 404)
            @ApiResponse(responseCode = "404", description = "Nenhuma matéria encontrada (EmptyListException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<List<MateriaResponse>> findAll() {
        List<Materia> materias = materiaService.findAll();
        List<MateriaResponse> response = materias.stream()
                .map(MateriaMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(response);
    }
}