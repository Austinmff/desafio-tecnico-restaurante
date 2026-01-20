package br.comdbserver.almocovotacao.repository;

import br.comdbserver.almocovotacao.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    Optional<Restaurante> findByNome(String nome);
}
