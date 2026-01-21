package br.comdbserver.almocovotacao.controller;

import br.comdbserver.almocovotacao.entity.Profissional;
import br.comdbserver.almocovotacao.repository.ProfissionalRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
@Tag(name = "Profissionais", description = "Cadastro e consulta de profissionais")
public class ProfissionalController {

    private final ProfissionalRepository repository;

    public ProfissionalController(ProfissionalRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Cadastrar profissional")
    @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso")
    @PostMapping
    public ResponseEntity<Profissional> criar(@RequestBody Profissional request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(request));
    }

    @Operation(summary = "Listar profissionais")
    @ApiResponse(responseCode = "200", description = "Lista de profissionais")
    @GetMapping
    public ResponseEntity<List<Profissional>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }
}
