package com.bazinga.dto.JogadorDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record JogadorUpdateDTO(
        @NotEmpty @NotNull @NotBlank
        String nome,
        @NotEmpty @NotNull @NotBlank
        String email,
        String fotoPath,
        List<Long> classesId,
        Boolean liderTime
) {
}
