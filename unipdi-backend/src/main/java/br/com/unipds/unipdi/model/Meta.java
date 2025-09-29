package br.com.unipds.unipdi.model;

import br.com.unipds.unipdi.dto.MetaRequestDto;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.UUID;

@DynamoDbBean
public class Meta {
    private UUID id;
    private String descricao;
    private boolean concluida;

    public Meta() {}

    public Meta(String descricao, boolean concluida) {
        this.id = UUID.randomUUID();
        this.descricao = descricao;
        this.concluida = concluida;
    }

    public static Meta fromDto(MetaRequestDto dto) {
        return new Meta(dto.descricao(), false);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}
