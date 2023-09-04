package com.example.demo.dto;

public class ContagemPorFuncionario {

    private String nomeFuncionario;
    private Long quantidadeResgates;

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public Long getQuantidadeResgates() {
        return quantidadeResgates;
    }

    public void setQuantidadeResgates(Long quantidadeResgates) {
        this.quantidadeResgates = quantidadeResgates;
    }
}
