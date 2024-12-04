package com.bazinga.config.security;

public record TokenInfo(
        Long id,
        String subject,
        String perfil,
        Boolean liderTime,
        Long timeId ) {
}
