package br.comdbserver.almocovotacao;

import br.comdbserver.almocovotacao.entity.Profissional;
import br.comdbserver.almocovotacao.repository.ProfissionalRepository;
import br.comdbserver.almocovotacao.repository.RestauranteRepository;
import br.comdbserver.almocovotacao.repository.VotoRepository;
import br.comdbserver.almocovotacao.service.VotacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class VotacaoServiceTest {

    @Autowired
    private VotacaoService service;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private VotoRepository votoRepository;

    @BeforeEach
    void setup() {
        votoRepository.deleteAll();
        restauranteRepository.deleteAll();
        profissionalRepository.deleteAll();
    }

    @Test
    void naoDevePermitirDoisVotosDoMesmoProfissionalNoMesmoDia() {

        Profissional profissional = profissionalRepository
                .save(new Profissional("João"));

        service.votar(profissional.getId(), "Restaurante A");

        assertThrows(IllegalStateException.class, () ->
                service.votar(profissional.getId(), "Sushi")
        );
    }

    @Test
    void naoDevePermitirMesmoRestauranteNaMesmaSemana() {

        Profissional profissional1 = profissionalRepository
                .save(new Profissional("João"));

        Profissional profissional2 = profissionalRepository
                .save(new Profissional("Maria"));

        service.votar(profissional1.getId(), "Restaurante A");

        assertThrows(IllegalStateException.class, () ->
                service.votar(profissional2.getId(), "Restaurante A")
        );
    }
}

