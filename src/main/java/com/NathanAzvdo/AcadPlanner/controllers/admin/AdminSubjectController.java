package com.NathanAzvdo.AcadPlanner.controllers.admin;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.SubjectMapper;
import com.NathanAzvdo.AcadPlanner.dtos.requests.SubjectRequest;
import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.SubjectResponse;
import com.NathanAzvdo.AcadPlanner.entities.Subject;
import com.NathanAzvdo.AcadPlanner.services.AdminSubjectService;

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
@RequestMapping("/admin/subject")
@Tag(name = "Matérias (Admin)", description = "Endpoints para gerenciamento de matérias")
@SecurityRequirement(name = "bearerAuth")
public class AdminSubjectController {

    private final AdminSubjectService subjectService;

    public AdminSubjectController(AdminSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/save")
    @Operation(summary = "Registra uma nova matéria", description = "Cria uma nova matéria no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matéria registrada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubjectResponse.class)) }),

            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: name vazio, créditos <= 0) (BusinessException / IllegalArgumentException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<SubjectResponse> save(@RequestBody SubjectRequest subjectRequest) {
        Subject newSubject = SubjectMapper.toSubject(subjectRequest);
        Subject subject = subjectService.save(newSubject);
        return ResponseEntity.ok().body(SubjectMapper.toMateriaResponse(subject));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma matéria", description = "Exclui uma matéria permanentemente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matéria deletada com sucesso!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(example = "Matéria deletada com sucesso!")) }),
            @ApiResponse(responseCode = "404", description = "Matéria não encontrada (InvalidIdException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<String> delete(
            @Parameter(description = "ID da matéria name ser excluída", example = "1")
            @PathVariable Long id) {
        subjectService.deleteById(id);
        return ResponseEntity.ok().body("Matéria deletada com sucesso!");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza uma matéria", description = "Atualiza os dados de uma matéria existente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matéria atualizada com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubjectResponse.class)) }),

            @ApiResponse(responseCode = "404", description = "Matéria não encontrada para atualização (InvalidIdException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<SubjectResponse> update(
            @RequestBody SubjectRequest subjectRequest,
            @Parameter(description = "ID da matéria name ser atualizada", example = "1")
            @PathVariable Long id) {

        Subject newSubject = SubjectMapper.toSubject(subjectRequest);
        Subject updatedSubject = subjectService.updateSubject(newSubject, id);
        return ResponseEntity.ok().body(SubjectMapper.toMateriaResponse(updatedSubject));
    }

    @PostMapping("/{subjectId}/pre-requisite/{preRequisiteId}")
    @Operation(summary = "Adiciona um pré-requisito", description = "Define uma matéria como pré-requisito de outra.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-requisito adicionado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubjectResponse.class)) }),

            @ApiResponse(responseCode = "404", description = "Matéria ou Pré-requisito não encontrados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<SubjectResponse> addPreRequisito(
            @Parameter(description = "ID da matéria (ex: Cálculo 2)", example = "2")
            @PathVariable Long subjectId,

            @Parameter(description = "ID do pré-requisito (ex: Cálculo 1)", example = "1")
            @PathVariable Long preRequisiteId) {

        Subject updatedSubject = subjectService.addPreRequisite(subjectId, preRequisiteId);
        return ResponseEntity.ok().body(SubjectMapper.toMateriaResponse(updatedSubject));
    }

    @DeleteMapping("/{subjectId}/pre-requisite/{preRequisiteId}")
    @Operation(summary = "Remove um pré-requisito", description = "Remove name dependência de pré-requisito de uma matéria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-requisito removido com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubjectResponse.class)) }),

            @ApiResponse(responseCode = "404", description = "Matéria ou Pré-requisito não encontrados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<SubjectResponse> removePreRequisito(
            @Parameter(description = "ID da matéria (ex: Cálculo 2)", example = "2")
            @PathVariable Long subjectId,

            @Parameter(description = "ID do pré-requisito (ex: Cálculo 1)", example = "1")
            @PathVariable Long preRequisiteId) {

        Subject updatedSubject = subjectService.removePreRequisito(subjectId, preRequisiteId);
        return ResponseEntity.ok().body(SubjectMapper.toMateriaResponse(updatedSubject));
    }

    @GetMapping
    @Operation(summary = "Lista todas as matérias", description = "Retorna uma lista de todas as matérias cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de matérias recuperada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SubjectResponse.class))) }),
            @ApiResponse(responseCode = "404", description = "Nenhuma matéria encontrada (EmptyListException)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    public ResponseEntity<List<SubjectResponse>> findAll() {
        List<Subject> subjects = subjectService.findAll();
        List<SubjectResponse> response = subjects.stream()
                .map(SubjectMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(response);
    }
}