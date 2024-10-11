package com.bazinga.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "controle" , name = "time")
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @ManyToMany(cascade = {} , fetch = FetchType.LAZY)
    @JoinTable(
            schema = "controle",
            name = "jogo_time",
            joinColumns = @JoinColumn(name = "time_id"),
            inverseJoinColumns = @JoinColumn(name = "jogo_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<JogoEntity> jogos = new HashSet<>();

    private String fotoPath;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "time")
    private List<Jogador> jogadores = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<JogoEntity> getJogos() {
        return jogos;
    }

    public void setJogos(Set<JogoEntity> jogos) {
        this.jogos = jogos;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }




    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
