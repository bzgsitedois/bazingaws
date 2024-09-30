package com.bazinga.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
public record TimeUpdateDTO (
        @NotEmpty @NotNull @NotBlank
        Long id,
        @NotEmpty @NotNull @NotBlank
        String nome,
        @NotEmpty @NotNull @NotBlank
        String descricao,
        @NotEmpty @NotNull @NotBlank
        List<Long> categoriaId,
        List<Long> jogadoresId,
        String foto_path
){
}
