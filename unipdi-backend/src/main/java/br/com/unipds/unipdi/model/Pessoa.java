package br.com.unipds.unipdi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pessoas")
public class Pessoa {
    @Id
    private String id;

    @Indexed(unique = true) // garante unicidade no MongoDB
    private String matricula;

    private String nome;

    private String curriculoUrl;

    public Pessoa(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

    public String getCurriculoUrl() {
        return curriculoUrl;
    }

    public void setCurriculoUrl(String curriculoUrl) {
        this.curriculoUrl = curriculoUrl;
    }

    public String getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }
}
