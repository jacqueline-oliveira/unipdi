package br.com.unipds.unipdi.dto;

import java.util.UUID;

public record MetaResponseDto(
        UUID id,
        String descricao,
        boolean concluida
) {
}
