package br.comdbserver.almocovotacao.repository;

import br.comdbserver.almocovotacao.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    Optional<Voto> findByProfissionalIdAndDataVoto(Long profissionalId, LocalDate dataVoto);

    List<Voto> findAllByDataVoto(LocalDate dataVoto);

    List<Voto> findAllByDataVotoBetween(LocalDate inicio, LocalDate fim);
}
