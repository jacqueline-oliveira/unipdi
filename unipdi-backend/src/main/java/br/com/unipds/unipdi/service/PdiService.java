package br.com.unipds.unipdi.service;

import br.com.unipds.unipdi.repository.PdiRepository;
import br.com.unipds.unipdi.repository.PessoaRepository;
import br.com.unipds.unipdi.dto.MetaRequestDto;
import br.com.unipds.unipdi.dto.MetaResponseDto;
import br.com.unipds.unipdi.dto.PdiRequestDto;
import br.com.unipds.unipdi.dto.PdiResponseDto;
import br.com.unipds.unipdi.model.Meta;
import br.com.unipds.unipdi.model.Pdi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdiService {
    private final PdiRepository pdiRepository;
    private final PessoaRepository pessoaRepository;

    public PdiService(PdiRepository pdiRepository, PessoaRepository pessoaRepository) {
        this.pdiRepository = pdiRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public PdiResponseDto criarPdi(PdiRequestDto dto) {
        pessoaRepository.findByMatricula(dto.matricula())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada para matrícula " + dto.matricula()));

        Pdi pdi = new Pdi(dto.matricula(), dto.dataInicio(), dto.dataFim(), dto.descricao());
        pdiRepository.save(pdi);

        return toResponse(pdi);
    }

    public List<PdiResponseDto> buscarPorMatricula(String matricula) {
        return pdiRepository.findByPessoaMatricula(matricula).stream()
                .map(this::toResponse)
                .toList();
    }

    public PdiResponseDto adicionarMeta(String pdiId, MetaRequestDto metaDto) {
        Pdi pdi = pdiRepository.findById(pdiId)
                .orElseThrow(() -> new RuntimeException("PDI não encontrado"));

        Meta meta = new Meta(metaDto.descricao(), metaDto.concluida());
        pdi.addMeta(meta);
        pdiRepository.save(pdi);

        return toResponse(pdi);
    }

    private PdiResponseDto toResponse(Pdi pdi) {
        return new PdiResponseDto(
                pdi.getId(),
                pdi.getPessoaMatricula(),
                pdi.getDataInicio(),
                pdi.getDataFim(),
                pdi.getDescricao(),
                pdi.getMetas().stream()
                        .map(m -> new MetaResponseDto(m.getId(), m.getDescricao(), m.isConcluida()))
                        .toList()
        );
    }

    public List<PdiResponseDto> buscarTodos() {
        return pdiRepository.findAll().stream()
                .map(p -> toResponse(p))
                .toList();

    }
}
