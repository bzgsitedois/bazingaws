package com.bazinga.entity.enums;

public enum Perfil {

    JOGADOR("Usuário é Jogador"),
    ADMIN("Administrador"),
    NADA("Usuário não é jogador");

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

}
