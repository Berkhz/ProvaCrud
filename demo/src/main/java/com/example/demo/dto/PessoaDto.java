package com.example.demo.dto;

public class PessoaDto {
    private Long id;
    private String nome;
    private Integer idade;
    private Long empregoId;

    public PessoaDto() {}

    public PessoaDto(Long id, String nome, Integer idade, Long empregoId) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.empregoId = empregoId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }

    public Long getEmpregoId() { return empregoId; }
    public void setEmpregoId(Long empregoId) { this.empregoId = empregoId; }
}