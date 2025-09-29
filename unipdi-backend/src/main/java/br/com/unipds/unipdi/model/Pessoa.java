package br.com.unipds.unipdi.model;

import br.com.unipds.unipdi.config.TableName;
import br.com.unipds.unipdi.dto.PessoaRequestDto;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@DynamoDbBean
@TableName(name = "Pessoa")
public class Pessoa {

    private String matricula;
    private String nome;
    private String curriculoUrl;
    private List<Pdi> pdis;

    public static Pessoa novaPessoa(PessoaRequestDto dto) {
        var pessoa = new Pessoa();
        pessoa.setMatricula(dto.matricula());
        pessoa.setNome(dto.nome());
        pessoa.setCurriculoUrl(dto.curriculoUrl());
        return pessoa;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("matricula")
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurriculoUrl() {
        return curriculoUrl;
    }

    public void setCurriculoUrl(String curriculoUrl) {
        this.curriculoUrl = curriculoUrl;
    }

    public List<Pdi> getPdis() {
        return pdis;
    }

    public void setPdis(List<Pdi> pdis) {
        this.pdis = pdis;
    }

    public void adicionaPdi(Pdi pdi) {
        this.pdis.add(pdi);
    }
}
