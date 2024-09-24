package com.bazinga.entity;

public enum Perfil {

    JOGADOR("Usuário é Jogador"),
    NADA("Usuário não é jogador");

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

}
