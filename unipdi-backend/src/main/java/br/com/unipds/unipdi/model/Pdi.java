package br.com.unipds.unipdi.model;

import br.com.unipds.unipdi.dto.PdiRequestDto;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DynamoDbBean
public class Pdi {
    private UUID id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String descricao;
    private List<Meta> metas = new ArrayList<>();

    public Pdi() {}

    public Pdi(LocalDate dataInicio, LocalDate dataFim, String descricao) {
        this.id = UUID.randomUUID();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
    }

    public static Pdi fromDto(PdiRequestDto dto){
        return new Pdi(dto.dataInicio(), dto.dataFim(), dto.descricao());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }

    public void adicionaMeta(Meta meta) {
        this.metas.add(meta);
    }
}
