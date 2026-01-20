package br.comdbserver.almocovotacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;


public class VotoRequest {

    @Schema(
            description = "ID do profissional que está votando",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private Long profissionalId;

    @Schema(
            description = "Nome do restaurante escolhido",
            example = "Restaurante A",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String nomeRestaurante;

    public Long getProfissionalId() { return profissionalId; }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId; }

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public void setNomeRestaurante(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }
}

