package com.bazinga.entity;

import com.bazinga.entity.enums.Categoria;
import com.bazinga.entity.enums.ClasseTF;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "controle" ,name = "categoria")
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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
        CategoriaEntity that = (CategoriaEntity) o;
        return Objects.equals(id, that.id) && categoria == that.categoria;
    }

    public int hashCode() {
        return Objects.hash(id, categoria);
    }
}
