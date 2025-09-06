package br.com.unipds.unipdi.repository;

import br.com.unipds.unipdi.model.Pdi;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PdiRepository extends MongoRepository<Pdi, String>  {
    List<Pdi> findByPessoaMatricula(String matricula);
}
