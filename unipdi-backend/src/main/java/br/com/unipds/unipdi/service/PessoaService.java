package br.com.unipds.unipdi.service;

import br.com.unipds.unipdi.dto.PessoaRequestDto;
import br.com.unipds.unipdi.dto.PessoaResponseDto;
import br.com.unipds.unipdi.exception.MatriculaJaCadastradaException;
import br.com.unipds.unipdi.model.Pessoa;
import br.com.unipds.unipdi.utils.Constantes;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final S3Template s3Template;
    private final DynamoDbTemplate dynamoDbTemplate;

    @Value("${app.s3.bucket-name}")
    private String bucketName;

    public PessoaService(S3Template s3Template, DynamoDbTemplate dynamoDbTemplate) {
        this.s3Template = s3Template;
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    public PessoaResponseDto cadastrarPessoa(PessoaRequestDto dto) {
        var pessoa = buscaPessoa(dto.matricula());

        if (!pessoa.isPresent()) {
            var novaPessoa = Pessoa.novaPessoa(dto);
            gravaPessoa(novaPessoa);
            return new PessoaResponseDto(novaPessoa.getMatricula(), novaPessoa.getNome(),
                    novaPessoa.getCurriculoUrl());
        }
        throw new MatriculaJaCadastradaException(dto.matricula());
    }

    public PessoaResponseDto buscarPorMatricula(String matricula) {
        Pessoa pessoa = buscaPessoa(matricula)
                .orElseThrow(() -> new IllegalArgumentException(Constantes.MATRICULA_NAO_ENCONTRADA + matricula));

        return new PessoaResponseDto(pessoa.getMatricula(), pessoa.getNome(), pessoa.getCurriculoUrl());
    }

    public PessoaResponseDto uploadCurriculo(String matricula, MultipartFile file) throws IOException {
        Pessoa pessoa = buscaPessoa(matricula)
                .orElseThrow(() -> new IllegalArgumentException(Constantes.MATRICULA_NAO_ENCONTRADA + matricula));

        var url = obterUrlCurriculo(pessoa.getMatricula(), file);

        pessoa.setCurriculoUrl(url);
        gravaPessoa(pessoa);

        return new PessoaResponseDto(pessoa.getMatricula(), pessoa.getNome(), pessoa.getCurriculoUrl());
    }

    private String obterUrlCurriculo(String pessoaId, MultipartFile file) throws IOException {
        String key = "curriculos/" + pessoaId + "/" + file.getOriginalFilename();

        s3Template.upload(bucketName, key, file.getInputStream());

        s3Template.upload(bucketName, key, file.getInputStream());

        // Retorna URL do arquivo no S3
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
    }

    public Optional<Pessoa> buscaPessoa(String matricula) {
        var key = Key.builder().partitionValue(matricula).build();
        var conditional = QueryConditional.keyEqualTo(key);

        var pessoa = dynamoDbTemplate.query(QueryEnhancedRequest.builder()
                        .queryConditional(conditional).build(),
                Pessoa.class);

        return pessoa.items().stream().findFirst();
    }

    public void gravaPessoa(Pessoa pessoa) {
        dynamoDbTemplate.save(pessoa);
    }

    public List<Pessoa> listarTodas() {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().build();
        ScanEnhancedRequest request = ScanEnhancedRequest.builder().build();
        DynamoDbTable<Pessoa> pessoaTable = enhancedClient.table("Pessoa", TableSchema.fromBean(Pessoa.class));

        PageIterable<Pessoa> results = pessoaTable.scan(request);

        List<Pessoa> pessoas = new ArrayList<>();
        for (Page<Pessoa> page : results) {
            pessoas.addAll(page.items());
        }

        return pessoas;
    }
}
