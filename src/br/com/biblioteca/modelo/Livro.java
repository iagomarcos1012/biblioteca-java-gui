package br.com.biblioteca.modelo;

import java.time.LocalDate;

public class Livro extends Entidade {
    private String titulo;
    private LocalDate dataPublicacao;
    private int qtdDisponivel;
    private Autor autor;
    private Genero genero;

    public Livro(int id, String titulo, Autor autor, Genero genero, int qtdDisponivel) {
        super(id);
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.qtdDisponivel = qtdDisponivel;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public LocalDate getDataPublicacao() {
        return this.dataPublicacao;
    }

    public int getQtdDisponivel() {
        return this.qtdDisponivel;
    }

    public Autor getAutor() {
        return this.autor;
    }

    public Genero getGenero() {
        return this.genero;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setQtdDisponivel(int qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public boolean estaDisponivel() {
        return this.qtdDisponivel > 0;
    }

    public void aumentarQtd() {
        this.qtdDisponivel++;
    }

    public void diminuirQtd() {
        if (estaDisponivel())
            this.qtdDisponivel--;
    }

    @Override
    public String toString() {
        return (super.toString() + ", Título: " + this.titulo + ", Autor: "
                + (this.autor != null ? this.autor.getNome() : "N/A") + ", Data de Publicação: " + this.dataPublicacao
                + ", Exemplares Disponíveis: "
                + this.qtdDisponivel);
    }
}