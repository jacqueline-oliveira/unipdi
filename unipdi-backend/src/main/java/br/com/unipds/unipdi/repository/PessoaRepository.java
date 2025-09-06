package br.com.unipds.unipdi.repository;

import br.com.unipds.unipdi.model.Pessoa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PessoaRepository extends MongoRepository<Pessoa, String> {
    Optional<Pessoa> findByMatricula(String matricula);
    boolean existsByMatricula(String matricula);
}
