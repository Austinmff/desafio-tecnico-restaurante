package br.comdbserver.almocovotacao.repository;

import br.comdbserver.almocovotacao.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    Optional<Profissional> findByNome(String nome);
}
