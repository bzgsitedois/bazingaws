package com.bazinga.entity;

import com.bazinga.entity.enums.Categoria;
import jakarta.persistence.*;

@Entity
@Table(schema = "controle" , name = "time")
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private String fotoPath;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
