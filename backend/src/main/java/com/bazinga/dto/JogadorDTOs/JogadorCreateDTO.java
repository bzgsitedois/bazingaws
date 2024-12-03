package com.bazinga.dto.JogadorDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record JogadorCreateDTO(

        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha,

        String perfil,

        String fotoPath,
        List<Long> classesId,
        Long time_id,
        Boolean liderTime
) {
}

