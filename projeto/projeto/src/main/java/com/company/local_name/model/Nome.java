package com.company.local_name.model;

public class Nome {
    private String localidade;
    private String nome;
    private int frequencia;
    private int rank;
    
    public Nome(String localidade, String nome, int frequencia, int rank) {
        this.localidade = localidade;
        this.nome = nome;
        this.frequencia = frequencia;
        this.rank = rank;
    }

    public String getCidade() {
        return localidade;
    }

    public void setCidade(String localidade) {
        this.localidade = localidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome_nome) {
        this.nome = nome_nome;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(int frequencia_nome) {
        this.frequencia = frequencia_nome;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank_nome) {
        this.rank = rank_nome;
    }

}
