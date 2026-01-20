package br.comdbserver.almocovotacao.controller;

import br.comdbserver.almocovotacao.dto.VotoRequest;
import br.comdbserver.almocovotacao.dto.ResultadoVotacaoResponse;
import br.comdbserver.almocovotacao.service.VotacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/votacao")
@Tag(name = "Votação", description = "Endpoints responsáveis pela votação do almoço")
public class VotacaoController {

    private final VotacaoService service;

    public VotacaoController(VotacaoService service) {
        this.service = service;
    }

    @Operation(
            summary = "Realizar voto",
            description = "Permite que um profissional vote em um restaurante para o almoço do dia."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Restaurante já escolhido na semana"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @PostMapping("/votar")
    public ResponseEntity<Void> votar(
            @Valid @RequestBody VotoRequest request
    ) {
        service.votar(
                request.getProfissionalId(),
                request.getNomeRestaurante()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Resultado do dia",
            description = "Retorna o restaurante com maior número de votos no dia corrente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum voto registrado no dia")
    })
    @GetMapping("/resultado")
    public ResponseEntity<ResultadoVotacaoResponse> resultadoDoDia() {
        return ResponseEntity.ok(service.obterResultadoDoDia());
    }
}


