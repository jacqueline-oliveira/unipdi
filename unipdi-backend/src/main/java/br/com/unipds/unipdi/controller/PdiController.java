package br.com.unipds.unipdi.controller;

import br.com.unipds.unipdi.dto.MetaRequestDto;
import br.com.unipds.unipdi.dto.PdiRequestDto;
import br.com.unipds.unipdi.dto.PdiResponseDto;
import br.com.unipds.unipdi.service.PdiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pdis")
public class PdiController {
    private final PdiService pdiService;

    public PdiController(PdiService pdiService) {
        this.pdiService = pdiService;
    }

    @PostMapping
    public PdiResponseDto criarPdi(@RequestBody PdiRequestDto dto) {
        return pdiService.criarPdi(dto);
    }

    @GetMapping("/{matricula}")
    public List<PdiResponseDto> buscarPorMatricula(@PathVariable String matricula) {
        return pdiService.buscarPorMatricula(matricula);
    }

    @GetMapping
    public List<PdiResponseDto> buscarTodos() {
        return pdiService.buscarTodos();
    }

    @PostMapping("/{pdiId}/metas")
    public PdiResponseDto adicionarMeta(@PathVariable String pdiId, @RequestBody MetaRequestDto dto) {
        return pdiService.adicionarMeta(pdiId, dto);
    }
}
