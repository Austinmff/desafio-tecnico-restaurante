package br.comdbserver.almocovotacao.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "voto")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @Column(nullable = false)
    private LocalDate dataVoto;

    //  CONSTRUTOR VAZIO
    public Voto() {
    }

    //  CONSTRUTOR COM 3 ARGUMENTOS
    public Voto(Profissional profissional, Restaurante restaurante, LocalDate dataVoto) {
        this.profissional = profissional;
        this.restaurante = restaurante;
        this.dataVoto = dataVoto;
    }

    //  GETTERS (ESSENCIAIS)
    public Long getId() {
        return id;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public LocalDate getDataVoto() {
        return dataVoto;
    }

}

