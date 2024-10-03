package com.bazinga.entity;
import com.bazinga.entity.enums.Tamanho;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "controle", name = "tamanho")
public class TamanhoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TamanhoEntity that = (TamanhoEntity) o;
        return Objects.equals(id, that.id) && tamanho == that.tamanho;
    }

    public int hashCode() {
        return Objects.hash(id, tamanho);
    }
}
