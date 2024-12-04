package com.bazinga.dto.TimeDTOs;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
public record TimeUpdateDTO (

        @NotBlank
        String nome,
       @NotBlank
        String descricao,
        @NotEmpty
        List<Long> jogoId,
        String foto_path
){
}
