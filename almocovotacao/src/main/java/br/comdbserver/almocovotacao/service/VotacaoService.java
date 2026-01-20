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

        // 1️) Profissional existe
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

        // 2️) Um voto por dia
        if (votoRepository.findByProfissionalIdAndDataVoto(profissionalId, hoje).isPresent()) {
            throw new IllegalStateException("Profissional já votou hoje");
        }

        // 3️) Restaurante (cria se não existir)
        Restaurante restaurante = restauranteRepository.findByNome(nomeRestaurante)
                .orElseGet(() -> restauranteRepository.save(new Restaurante(nomeRestaurante)));

        // 4️) Restaurante não pode repetir na semana
        validarRestauranteNaSemana(restaurante.getId(), hoje);

        // 5️) Salva voto
        votoRepository.save(new Voto(profissional, restaurante, hoje));
    }

    // =========================
    // RESULTADO DO DIA
    // =========================
    public ResultadoVotacaoResponse obterResultadoDoDia() {

        LocalDate hoje = LocalDate.now();
        List<Voto> votosHoje = votoRepository.findAllByDataVoto(hoje);

        if (votosHoje.isEmpty()) {
            throw new IllegalStateException("Nenhuma votação registrada hoje");
        }

        Map<Restaurante, Long> contagem =
                votosHoje.stream()
                        .collect(Collectors.groupingBy(
                                Voto::getRestaurante,
                                Collectors.counting()
                        ));

        Map.Entry<Restaurante, Long> vencedor =
                contagem.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow();

        return new ResultadoVotacaoResponse(
                vencedor.getKey().getId(),
                vencedor.getKey().getNome(),
                vencedor.getValue().intValue()
        );
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
