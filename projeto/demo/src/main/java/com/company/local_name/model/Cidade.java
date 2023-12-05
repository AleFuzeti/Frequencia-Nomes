package com.company.local_name.model;

public class Cidade {
    private String id;
    private String nome;
    private String sigla_estado;

    public Cidade(String id, String nome, String sigla_estado) {
        this.id = id;
        this.nome = nome;
        this.sigla_estado = sigla_estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id_cidade) {
        this.id = id_cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla_estado() {
        return sigla_estado;
    }

    public void setSigla_estado(String sigla_estado) {
        this.sigla_estado = sigla_estado;
    }

}
