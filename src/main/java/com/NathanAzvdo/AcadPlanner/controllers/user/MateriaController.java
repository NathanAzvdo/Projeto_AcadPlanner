package com.NathanAzvdo.AcadPlanner.controllers.user;

import com.NathanAzvdo.AcadPlanner.dtos.mappers.MateriaMapper;
import com.NathanAzvdo.AcadPlanner.dtos.responses.ErrorResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaBasicaResponse;
import com.NathanAzvdo.AcadPlanner.dtos.responses.MateriaResponse;
import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.services.MateriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia")
@Tag(name = "Matérias (Usuário)", description = "Endpoints para usuários logados consultarem e gerenciarem suas matérias")
@SecurityRequirement(name = "bearerAuth")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @PostMapping("/concluir/{idMateria}")
    @Operation(summary = "Conclui uma matéria",
            description = "Marca uma matéria que o usuário está cursando como 'concluída'. Remove de 'em andamento'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matéria concluída com sucesso!",
                    content = @Content(schema = @Schema(example = "Matéria concluída com sucesso!"))),
            @ApiResponse(responseCode = "400", description = "O usuário não está cursando esta matéria (BusinessException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário ou Matéria não encontrados (InvalidIdException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> concluirMateria(@PathVariable Long idMateria, @AuthenticationPrincipal User user){
        materiaService.concluirMateria(idMateria, user);
        return ResponseEntity.ok().body("Matéria concluída com sucesso!");
    }

    @PostMapping("/ingressar/{idMateria}")
    @Operation(summary = "Ingressa em uma matéria",
            description = "Adiciona uma matéria à lista de 'matérias em andamento' do usuário, se os pré-requisitos forem atendidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingressou na matéria com sucesso!",
                    content = @Content(schema = @Schema(example = "Ingressou na matéria com sucesso!"))),
            @ApiResponse(responseCode = "400", description = "Pré-requisitos não atendidos ou usuário já está cursando (BusinessException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário ou Matéria não encontrados (InvalidIdException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> ingressarMateria(@PathVariable Long idMateria, @AuthenticationPrincipal User user){
        materiaService.novaMateriaEmAndamento(idMateria, user.getId());
        return ResponseEntity.ok().body("Ingressou na matéria com sucesso!");
    }

    @GetMapping("/concluidas")
    @Operation(summary = "Lista matérias concluídas",
            description = "Retorna a lista de matérias que o usuário logado já concluiu.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de matérias concluídas recuperada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MateriaBasicaResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma matéria concluída encontrada (EmptyListException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<MateriaBasicaResponse>> findMateriasConcluidas(@AuthenticationPrincipal User user){


        List<MateriaBasicaResponse> materias = materiaService.materiasConcluidas(user.getId()).stream()
                .map(MateriaMapper::toMateriaBasicaResponse)
                .toList();
        return ResponseEntity.ok().body(materias);
    }

    @GetMapping("/em-andamento")
    @Operation(summary = "Lista matérias em andamento",
            description = "Retorna a lista de matérias que o usuário logado está cursando no momento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de matérias em andamento recuperada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MateriaResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma matéria em andamento encontrada (EmptyListException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<MateriaResponse>> findMateriasEmAndamento(@AuthenticationPrincipal User user){
        List<MateriaResponse> materias = materiaService.materiasEmAndamento(user.getId()).stream()
                .map(MateriaMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(materias);
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Lista matérias disponíveis (Sugestão)",
            description = "Retorna a lista de matérias que o usuário pode cursar (já cumpriu pré-requisitos e não está cursando/concluída).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de matérias disponíveis recuperada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MateriaResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma matéria disponível para cursar (EmptyListException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<MateriaResponse>> findMateriasDisponiveis(@AuthenticationPrincipal User user){
        List<MateriaResponse> materias = materiaService.findMateriasDisponiveis(user).stream()
                .map(MateriaMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(materias);
    }

    @GetMapping("/materias-curso")
    @Operation(summary = "Lista todas as matérias do curso",
            description = "Retorna todas as matérias associadas ao curso do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de matérias do curso recuperada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MateriaResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma matéria encontrada para este curso (EmptyListException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<MateriaResponse>> findByCurso(@AuthenticationPrincipal User user){
        List<MateriaResponse> materias = materiaService.findMateriasCurso(user.getCurso().getId()).stream()
                .map(MateriaMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(materias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca matéria por ID",
            description = "Retorna os detalhes de uma matéria específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matéria encontrada",
                    content = @Content(schema = @Schema(implementation = MateriaResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Matéria não encontrada (InvalidIdException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<MateriaResponse> findById(@PathVariable Long id) {
        Materia materia = materiaService.findById(id);
        return ResponseEntity.ok().body(MateriaMapper.toMateriaResponse(materia));
    }

    @GetMapping("/{materiaId}/pre-requisito")
    @Operation(summary = "Busca pré-requisitos da matéria",
            description = "Retorna a lista de matérias que são pré-requisitos para a matéria informada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pré-requisitos recuperada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MateriaResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado (token inválido)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Matéria não encontrada ou não possui pré-requisitos (InvalidIdException/EmptyListException)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<MateriaResponse>> getPreRequisitos(@PathVariable Long materiaId) {
        List<Materia> preRequisitos = materiaService.getPreRequisitos(materiaId);
        List<MateriaResponse> response = preRequisitos.stream()
                .map(MateriaMapper::toMateriaResponse)
                .toList();
        return ResponseEntity.ok().body(response);
    }
}