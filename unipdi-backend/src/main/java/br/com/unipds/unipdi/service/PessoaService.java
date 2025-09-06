package br.com.unipds.unipdi.service;

import br.com.unipds.unipdi.dto.PessoaRequestDto;
import br.com.unipds.unipdi.dto.PessoaResponseDto;
import br.com.unipds.unipdi.model.Pessoa;
import br.com.unipds.unipdi.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public PessoaResponseDto cadastrarPessoa(PessoaRequestDto dto) {
        if (pessoaRepository.existsByMatricula(dto.matricula())) {
            throw new IllegalArgumentException("Matrícula já cadastrada: " + dto.matricula());
        }

        Pessoa pessoa = new Pessoa(dto.matricula(), dto.nome());
        Pessoa salva = pessoaRepository.save(pessoa);

        return new PessoaResponseDto(salva.getId(), salva.getMatricula(), salva.getNome());
    }

    public PessoaResponseDto buscarPorMatricula(String matricula) {
        Pessoa pessoa = pessoaRepository.findByMatricula(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada com matrícula " + matricula));

        return new PessoaResponseDto(pessoa.getId(), pessoa.getMatricula(), pessoa.getNome());
    }

    public List<PessoaResponseDto> buscarTodos() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream().map(p ->
                new PessoaResponseDto(p.getId(),p.getMatricula(), p.getNome()))
                .toList();
    }
}
