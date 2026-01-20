package br.comdbserver.almocovotacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resultado da votação do almoço no dia")
public class ResultadoVotacaoResponse {

    @Schema(
            description = "ID do restaurante vencedor",
            example = "3"
    )
    private final Long restauranteId;

    @Schema(
            description = "Nome do restaurante vencedor",
            example = "Restaurante A"
    )
    private final String restauranteNome;

    @Schema(
            description = "Quantidade total de votos recebidos",
            example = "5"
    )
    private final Integer totalVotos;

    public ResultadoVotacaoResponse(
            Long restauranteId,
            String restauranteNome,
            Integer totalVotos
    ) {
        this.restauranteId = restauranteId;
        this.restauranteNome = restauranteNome;
        this.totalVotos = totalVotos;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public String getRestauranteNome() {
        return restauranteNome;
    }

    public Integer getTotalVotos() {
        return totalVotos;
    }
}
