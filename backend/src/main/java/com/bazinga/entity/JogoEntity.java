package com.bazinga.entity;

import com.bazinga.entity.enums.Jogo;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "controle" ,name = "jogo")
public class JogoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Jogo jogo;

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JogoEntity that = (JogoEntity) o;
        return Objects.equals(id, that.id) && jogo == that.jogo;
    }

    public int hashCode() {
        return Objects.hash(id, jogo);
    }
}
