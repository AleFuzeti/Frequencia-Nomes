package com.company.local_name.model;

public class Nome {
    private String id;
    private String nome;
    private int frequencia;
    private int rank;
    
    public Nome(String id, String nome, int frequencia, int rank) {
        this.id = id;
        this.nome = nome;
        this.frequencia = frequencia;
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id_nome) {
        this.id = id_nome;
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
