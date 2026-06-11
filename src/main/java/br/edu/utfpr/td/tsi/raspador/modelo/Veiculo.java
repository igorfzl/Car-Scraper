package br.edu.utfpr.td.tsi.raspador.modelo;

public class Veiculo {
    private String titulo;
    private String preco;
    private String ano;
    private String linkOriginal;


    public Veiculo() {
    }

    public Veiculo(String titulo, String preco, String ano, String linkOriginal) {
        this.titulo = titulo;
        this.preco = preco;
        this.ano = ano;
        this.linkOriginal = linkOriginal;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getLinkOriginal() {
        return linkOriginal;
    }

    public void setLinkOriginal(String linkOriginal) {
        this.linkOriginal = linkOriginal;
    }

    @Override
    public String toString() {
        return "Veiculo [" + ano + "] " + titulo + " | " + preco + "\nLink: " + linkOriginal;
    }
}
