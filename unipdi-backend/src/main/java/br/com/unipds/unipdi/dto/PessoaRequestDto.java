package br.com.unipds.unipdi.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PessoaRequestDto(
        @NotBlank String matricula,
        @NotBlank String nome,
        List<PdiRequestDto> pdis,
        String curriculoUrl
) {
}
