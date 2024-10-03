package com.bazinga.entity.enums;

public enum Jogo {
    TF2("TF2"),
    VALORANT("VALORANT"),
    BRAWLHALLA("BRAWLHALLA"),
    ROCKETLEAGUE("ROCKETLEAGUE"),
    FORTNITE("FORTNITE"),
    CS2("CS2");

    Jogo(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }
}
