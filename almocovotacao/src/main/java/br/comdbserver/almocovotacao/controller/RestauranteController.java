package br.comdbserver.almocovotacao.controller;

import br.comdbserver.almocovotacao.entity.Restaurante;
import br.comdbserver.almocovotacao.repository.RestauranteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "Cadastro e consulta de restaurantes")
public class RestauranteController {

    private final RestauranteRepository repository;

    public RestauranteController(RestauranteRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Cadastrar restaurante")
    @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso")
    @PostMapping
    public ResponseEntity<Restaurante> criar(@RequestBody Restaurante request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(request));
    }

    @Operation(summary = "Listar restaurantes")
    @ApiResponse(responseCode = "200", description = "Lista de restaurantes")
    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }
}

