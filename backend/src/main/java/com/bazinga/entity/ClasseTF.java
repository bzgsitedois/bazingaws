package com.bazinga.entity;

public enum ClasseTF {
    SPY("SPY"),
    SNIPER("SNIPER"),
    MEDIC("MEDIC"),
    SOLDIER("SOLDIER"),
    DEMOMAN("DEMOMAN"),
    PYRO("PYRO"),
    ENGINEER("ENGINEER"),
    HEAVY("HEAVY"),
    SCOUT("SCOUT");

    ClasseTF(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

}

