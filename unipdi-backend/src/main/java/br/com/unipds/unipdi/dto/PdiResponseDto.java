package br.com.unipds.unipdi.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PdiResponseDto(
        UUID id,
        LocalDate dataInicio,
        LocalDate dataFim,
        String descricao,
        List<MetaResponseDto> metas
) {
}
