package com.bazinga.dto.ProdutoDTOs;

public record ProdutoFilter (
        String nome,
        double preco,
        double desconto,
        int quantidade
)
{ }
