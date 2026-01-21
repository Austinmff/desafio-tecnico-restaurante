package br.comdbserver.almocovotacao.controller;

import br.comdbserver.almocovotacao.dto.ResultadoVotacaoResponse;
import br.comdbserver.almocovotacao.dto.VotoRequest;
import br.comdbserver.almocovotacao.service.VotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votacao")
@Tag(name = "Votação", description = "Processo de votação do almoço")
public class VotacaoController {

    private final VotacaoService service;

    public VotacaoController(VotacaoService service) {
        this.service = service;
    }

    @Operation(
            summary = "Registrar voto",
            description = "Permite que um profissional vote em um restaurante no dia atual"
    )
    @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso")
    @PostMapping("/votar")
    public ResponseEntity<Void> votar(@Valid @RequestBody VotoRequest request) {
        service.votar(request.getProfissionalId(), request.getNomeRestaurante());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Resultado do dia",
            description = "Retorna o restaurante mais votado do dia"
    )
    @ApiResponse(responseCode = "200", description = "Resultado retornado")
    @GetMapping("/resultado")
    public ResponseEntity<ResultadoVotacaoResponse> resultado() {
        return ResponseEntity.ok(service.resultadoDoDia());
    }
}



