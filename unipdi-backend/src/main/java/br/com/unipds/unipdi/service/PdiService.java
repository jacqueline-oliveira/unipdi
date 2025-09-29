package br.com.unipds.unipdi.service;

import br.com.unipds.unipdi.dto.MetaRequestDto;
import br.com.unipds.unipdi.dto.MetaResponseDto;
import br.com.unipds.unipdi.dto.PdiRequestDto;
import br.com.unipds.unipdi.dto.PdiResponseDto;
import br.com.unipds.unipdi.model.Meta;
import br.com.unipds.unipdi.model.Pdi;
import br.com.unipds.unipdi.utils.Constantes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PdiService {
    private final PessoaService pessoaService;

    public PdiService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    public PdiResponseDto criarPdi(PdiRequestDto dto) {
        var pessoa = pessoaService.buscaPessoa(dto.matricula())
                .orElseThrow(() -> new RuntimeException(Constantes.MATRICULA_NAO_ENCONTRADA + dto.matricula()));

        Pdi pdi = new Pdi(dto.dataInicio(), dto.dataFim(), dto.descricao());

        if (pessoa.getPdis() == null) {
            pessoa.setPdis(new ArrayList<>());
        }

        pessoa.getPdis().add(pdi);
        pessoaService.gravaPessoa(pessoa);

        return toResponse(pdi);
    }

    public List<PdiResponseDto> buscarPorMatricula(String matricula) {
        var pessoa = pessoaService.buscaPessoa(matricula)
                .orElseThrow(() -> new RuntimeException(Constantes.MATRICULA_NAO_ENCONTRADA + matricula));

        List<Pdi> pdis = pessoa.getPdis();

        if (pdis == null || pdis.isEmpty()) {
            return List.of();
        }
        return pessoa.getPdis().stream()
                .map(this::toResponse)
                .toList();

    }

    public PdiResponseDto adicionarMeta(String matricula, String pdiId, MetaRequestDto metaDto) {
        var pessoa = pessoaService.buscaPessoa(matricula)
                .orElseThrow(() -> new RuntimeException(Constantes.MATRICULA_NAO_ENCONTRADA + matricula));

        Pdi pdi = pessoa.getPdis().stream()
                .filter(p -> p.getId().toString().equals(pdiId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("PDI não encontrado"));

        Meta meta = new Meta(metaDto.descricao(), metaDto.concluida());

        if (pdi.getMetas() == null) {
            pdi.setMetas(new ArrayList<>());
        }

        pdi.adicionaMeta(meta);
        pessoaService.gravaPessoa(pessoa);

        return toResponse(pdi);
    }

    private PdiResponseDto toResponse(Pdi pdi) {
        return new PdiResponseDto(
                pdi.getId(),
                pdi.getDataInicio(),
                pdi.getDataFim(),
                pdi.getDescricao(),
                pdi.getMetas().stream()
                        .map(m -> new MetaResponseDto(m.getId(), m.getDescricao(), m.isConcluida()))
                        .toList()
        );
    }
}
