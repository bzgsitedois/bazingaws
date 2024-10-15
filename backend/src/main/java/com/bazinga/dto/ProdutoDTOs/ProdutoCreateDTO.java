package com.bazinga.dto.ProdutoDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProdutoCreateDTO(

@NotEmpty @NotNull @NotBlank
long id,
@NotEmpty @NotNull @NotBlank
String nome,
@NotEmpty @NotNull @NotBlank
int quantidade,
String logo,
double preco,
double desconto,
double frete,
String materiais,
List<Enum> tamanho,
String fotoPath
){}
