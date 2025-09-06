package br.com.unipds.unipdi.dto;

import jakarta.validation.constraints.NotBlank;

public record PessoaRequestDto(
        @NotBlank String matricula,
        @NotBlank String nome
) {
}
