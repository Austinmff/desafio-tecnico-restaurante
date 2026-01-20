package br.comdbserver.almocovotacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurante")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    //  CONSTRUTOR VAZIO (OBRIGATÓRIO PARA JPA)
    public Restaurante() {
    }

    //  CONSTRUTOR COM ARGUMENTO
    public Restaurante(String nome) {
        this.nome = nome;
    }

    //  GETTERS
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    //  SETTERS
    public void setNome(String nome) {
        this.nome = nome;
    }
}
