package br.com.biblioteca.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Autor extends Pessoa {
    private String biografia;
    private final List<Livro> livrosEscritos;

    public Autor(String biografia, int id, String nome, LocalDate dataNasc, String contato) {
        super(id, nome, dataNasc, contato);
        this.biografia = biografia;
        this.livrosEscritos = new ArrayList<>();
    }

    public String getBiografia() {
        return this.biografia;
    }

    public List<Livro> getLivrosEscritos() {
        return this.livrosEscritos;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setLivrosEscritos(Livro livro) {
        this.livrosEscritos.add(livro);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.livrosEscritos.size(); i++) {
            builder.append(this.livrosEscritos.get(i).getTitulo());
            if (i < this.livrosEscritos.size() - 1) {
                builder.append(", ");
            }
        }
        return (super.toString() + ", Biografia: " + this.biografia + ", Livros Escritos: "
                + builder);
    }
}
