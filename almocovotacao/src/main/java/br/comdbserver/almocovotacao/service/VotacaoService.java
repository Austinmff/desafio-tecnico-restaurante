package br.comdbserver.almocovotacao.service;

import br.comdbserver.almocovotacao.dto.ResultadoVotacaoResponse;
import br.comdbserver.almocovotacao.entity.Profissional;
import br.comdbserver.almocovotacao.entity.Restaurante;
import br.comdbserver.almocovotacao.entity.Voto;
import br.comdbserver.almocovotacao.repository.ProfissionalRepository;
import br.comdbserver.almocovotacao.repository.RestauranteRepository;
import br.comdbserver.almocovotacao.repository.VotoRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VotacaoService {

    private final ProfissionalRepository profissionalRepository;
    private final RestauranteRepository restauranteRepository;
    private final VotoRepository votoRepository;

    public VotacaoService(
            ProfissionalRepository profissionalRepository,
            RestauranteRepository restauranteRepository,
            VotoRepository votoRepository
    ) {
        this.profissionalRepository = profissionalRepository;
        this.restauranteRepository = restauranteRepository;
        this.votoRepository = votoRepository;
    }

    // =========================
    // VOTAR
    // =========================
    public void votar(Long profissionalId, String nomeRestaurante) {

        LocalDate hoje = LocalDate.now();

        // 1 voto por dia por profissional
        if (votoRepository.findByProfissionalIdAndDataVoto(profissionalId, hoje).isPresent()) {
            throw new IllegalStateException("Profissional já votou hoje");
        }

        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

        Restaurante restaurante = restauranteRepository.findByNome(nomeRestaurante)
                .orElseGet(() -> restauranteRepository.save(new Restaurante(nomeRestaurante)));

        Voto voto = new Voto(profissional, restaurante, hoje);
        votoRepository.save(voto);
    }


    // =========================
    // RESULTADO DO DIA
    // =========================
    public ResultadoVotacaoResponse resultadoDoDia() {
        LocalDate hoje = LocalDate.now();
        List<Voto> votos = votoRepository.findAllByDataVoto(hoje);

        Map<Restaurante, Long> contagem =
                votos.stream()
                        .collect(Collectors.groupingBy(Voto::getRestaurante, Collectors.counting()));

        Restaurante vencedor = contagem.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalStateException("Nenhum voto para hoje"))
                .getKey();

        Long total = contagem.get(vencedor);

        return new ResultadoVotacaoResponse(vencedor.getId(), vencedor.getNome(), total.intValue());
    }


    // =========================
    // REGRAS AUXILIARES
    // =========================
    private void validarRestauranteNaSemana(Long restauranteId, LocalDate data) {

        LocalDate inicioSemana = data.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = data.with(DayOfWeek.FRIDAY);

        boolean jaVenceuSemana =
                votoRepository.findAllByDataVotoBetween(inicioSemana, fimSemana)
                        .stream()
                        .anyMatch(v -> v.getRestaurante().getId().equals(restauranteId));

        if (jaVenceuSemana) {
            throw new IllegalStateException("Restaurante já escolhido nesta semana");
        }
    }
}
