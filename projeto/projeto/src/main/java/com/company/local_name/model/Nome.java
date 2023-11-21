package com.company.local_name.model;

public class Nome {
    private String cidade;
    private String nome;
    private int frequencia;
    private int rank;
    
    public Nome(String cidade, String nome, int frequencia, int rank) {
        this.cidade = cidade;
        this.nome = nome;
        this.frequencia = frequencia;
        this.rank = rank;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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
