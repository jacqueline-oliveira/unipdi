package br.com.unipds.unipdi.controller;

import br.com.unipds.unipdi.dto.PessoaRequestDto;
import br.com.unipds.unipdi.dto.PessoaResponseDto;
import br.com.unipds.unipdi.model.Pessoa;
import br.com.unipds.unipdi.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDto> cadastrar(@RequestBody PessoaRequestDto dto) {
        return ResponseEntity.ok(pessoaService.cadastrarPessoa(dto));
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<PessoaResponseDto> buscar(@PathVariable String matricula) {
        return ResponseEntity.ok(pessoaService.buscarPorMatricula(matricula));
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listar() {
        return ResponseEntity.ok(pessoaService.listarTodas());
    }

}
