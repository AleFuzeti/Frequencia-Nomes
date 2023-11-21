package main.java.model;

public class Nome {
    private String id;
    private String nome;
    private String genero;
    
    public Nome(String id, String nome, String genero) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero_nome) {
        this.genero = genero_nome;
    }

}
