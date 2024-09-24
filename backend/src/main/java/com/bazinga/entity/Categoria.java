package com.bazinga.entity;

public enum Categoria {
    SIXES("6v6"),
    HL("9v9");

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

}
