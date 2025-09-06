package br.com.unipds.unipdi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class Meta {
    @Id
    private String id;
    private String descricao;
    private boolean concluida;

    public Meta() {}

    public Meta(String descricao, boolean concluida) {
        this.id = new ObjectId().toString();
        this.descricao = descricao;
        this.concluida = concluida;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }
}
