package com.example.demo.controller;

import com.example.demo.dto.PessoaDto;
import com.example.demo.entity.Emprego;
import com.example.demo.entity.Pessoa;
import com.example.demo.repository.IPessoaRepository;
import com.example.demo.repository.IEmpregoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private IPessoaRepository pessoaRepository;

    @Autowired
    private IEmpregoRepository empregoRepository;

    @GetMapping
    public List<PessoaDto> listar() {
        return pessoaRepository.findAll().stream().map(p ->
                new PessoaDto(
                        p.getId(),
                        p.getNome(),
                        p.getIdade(),
                        p.getEmprego() != null ? p.getEmprego().getId() : null
                )
        ).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<PessoaDto> criar(@RequestBody PessoaDto dto) {
        Emprego emprego = empregoRepository.findById(dto.getEmpregoId())
                .orElseThrow(() -> new RuntimeException("Emprego não encontrado"));

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setIdade(dto.getIdade());
        pessoa.setEmprego(emprego);

        pessoa = pessoaRepository.save(pessoa);

        return ResponseEntity.ok(new PessoaDto(pessoa.getId(), pessoa.getNome(), pessoa.getIdade(), emprego.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDto> atualizar(@PathVariable Long id, @RequestBody PessoaDto dto) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        Emprego emprego = empregoRepository.findById(dto.getEmpregoId())
                .orElseThrow(() -> new RuntimeException("Emprego não encontrado"));

        pessoa.setNome(dto.getNome());
        pessoa.setIdade(dto.getIdade());
        pessoa.setEmprego(emprego);

        pessoa = pessoaRepository.save(pessoa);

        return ResponseEntity.ok(new PessoaDto(pessoa.getId(), pessoa.getNome(), pessoa.getIdade(), emprego.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        pessoaRepository.delete(pessoa);
        return ResponseEntity.noContent().build();
    }
}