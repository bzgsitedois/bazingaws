package com.bazinga.entity.enums;

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

    private String descricao;

    ClasseTF(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
