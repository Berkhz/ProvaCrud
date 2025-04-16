package com.example.demo.controller;

import com.example.demo.dto.EmpregoDto;
import com.example.demo.entity.Emprego;
import com.example.demo.repository.IEmpregoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empregos")
public class EmpregoController {

    @Autowired
    private IEmpregoRepository empregoRepository;

    @GetMapping
    public List<EmpregoDto> listarTodos() {
        return empregoRepository.findAll().stream().map(e ->
                new EmpregoDto(e.getId(), e.getNome(), e.getEndereco())
        ).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<EmpregoDto> criar(@RequestBody EmpregoDto dto) {
        Emprego emprego = new Emprego();
        emprego.setNome(dto.getNome());
        emprego.setEndereco(dto.getEndereco());
        emprego = empregoRepository.save(emprego);
        return ResponseEntity.ok(new EmpregoDto(emprego.getId(), emprego.getNome(), emprego.getEndereco()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpregoDto> atualizar(@PathVariable Long id, @RequestBody EmpregoDto dto) {
        Emprego emprego = empregoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprego não encontrado"));

        emprego.setNome(dto.getNome());
        emprego.setEndereco(dto.getEndereco());
        emprego = empregoRepository.save(emprego);

        return ResponseEntity.ok(new EmpregoDto(emprego.getId(), emprego.getNome(), emprego.getEndereco()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Emprego emprego = empregoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprego não encontrado"));

        empregoRepository.delete(emprego);
        return ResponseEntity.noContent().build();
    }
}