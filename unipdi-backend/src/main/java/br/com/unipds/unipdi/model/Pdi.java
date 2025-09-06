package br.com.unipds.unipdi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "pdis")
public class Pdi {
    @Id
    private String id;
    private String pessoaMatricula; // referência pela matrícula da pessoa
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String descricao;
    private List<Meta> metas = new ArrayList<>();

    public Pdi(String pessoaMatricula, LocalDate dataInicio, LocalDate dataFim, String descricao) {
        this.pessoaMatricula = pessoaMatricula;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String getPessoaMatricula() {
        return pessoaMatricula;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void addMeta(Meta meta) {
        this.metas.add(meta);
    }
}
