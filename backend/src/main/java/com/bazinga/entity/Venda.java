package com.bazinga.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (schema="seguranca", name ="venda")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String formaPagamento;

    private Date data;


    private String nomeComprador;

    private String nomeRecebedor = "Bagres.ltda";

    @ManyToMany
    private Set<Produto> produtos = new HashSet<>();
    @JoinTable(
            schema = "seguranca",
            name = "produto_venda",
            joinColumns = @JoinColumn(name = "venda_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    @ManyToOne(fetch = FetchType.LAZY, cascade ={})
    @JoinColumn(name= "jogador_id")
    private Jogador jogador;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForma_pagamento() {
        return formaPagamento;
    }

    public void setForma_pagamento(String forma_pagamento) {
        this.formaPagamento = forma_pagamento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNome_comprador() {
        return nomeComprador;
    }

    public void setNome_comprador(String nome_comprador) {
        this.nomeComprador = nome_comprador;
    }
    public String getNome_recebedor() {
        return nomeRecebedor;
    }
    public void setNome_recebedor(String nome_recebedor) {
        this.nomeRecebedor = nome_recebedor;
    }
}
