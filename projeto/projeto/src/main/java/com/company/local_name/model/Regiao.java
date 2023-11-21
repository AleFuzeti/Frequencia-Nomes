package com.company.local_name.model;

public class Regiao {
    private String id;
    private String sigla;
    private String nome;

    public Regiao(String id, String sigla, String nome) {
        this.id = id;
        this.sigla = sigla;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id_regiao) {
        this.id = id_regiao;
    }

    public String getSigla() {
        return sigla;
    }
    
    public void setSigla(String sigla_regiao) {
        this.sigla = sigla_regiao;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome_regiao) {
        this.nome = nome_regiao;
    }
    
}