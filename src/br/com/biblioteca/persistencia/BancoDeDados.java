package br.com.biblioteca.persistencia;

import br.com.biblioteca.modelo.*;

public class BancoDeDados {
    private final Persistente<Autor> autores;
    private final Persistente<Leitor> leitores;
    private final Persistente<Livro> livros;
    private final Persistente<Genero> generos;
    private final Persistente<Emprestimo> emprestimos;

    public BancoDeDados() {
        this.autores = new Persistente<>();
        this.leitores = new Persistente<>();
        this.livros = new Persistente<>();
        this.generos = new Persistente<>();
        this.emprestimos = new Persistente<>();
    }

    public Persistente<Autor> getAutores() {
        return this.autores;
    }

    public Persistente<Leitor> getLeitores() {
        return this.leitores;
    }

    public Persistente<Livro> getLivros() {
        return this.livros;
    }

    public Persistente<Genero> getGeneros() {
        return this.generos;
    }

    public Persistente<Emprestimo> getEmprestimos() {
        return this.emprestimos;
    }
}