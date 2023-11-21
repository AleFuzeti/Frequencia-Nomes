package com.company.local_name.model;

public class Estado {
    private String id;
    private String nome;
    private String sigla;
    private String sigla_reg;

    public Estado(String id, String nome, String sigla, String sigla_reg) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.sigla_reg = sigla_reg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id_estado) {
        this.id = id_estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome_estado) {
        this.nome = nome_estado;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla_estado) {
        this.sigla = sigla_estado;
    }

    public String getSigla_regiao() {
        return sigla_reg;
    }

    public void setSigla_regiao(String sigla_reg) {
        this.sigla_reg = sigla_reg;
    }

}
