package br.com.unipds.unipdi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record PdiRequestDto(
        @NotBlank String matricula,
        @NotBlank String descricao,
        @NotNull LocalDate dataInicio,
        @NotNull LocalDate dataFim
) {
}
