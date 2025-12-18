package br.com.biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;

public class Genero extends Entidade {
    private String genero;
    private final List<Livro> livrosDoGenero;

    public Genero(int id, String genero) {
        super(id);
        this.genero = genero;
        this.livrosDoGenero = new ArrayList<>();
    }

    public String getGenero() {
        return this.genero;
    }

    public List<Livro> getLivrosDoGenero() {
        return this.livrosDoGenero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setLivrosDoGenero(Livro livro) {
        this.livrosDoGenero.add(livro);
    }

    private String formatarLivrosDoGenero() {
        if (this.livrosDoGenero.isEmpty()) {
            return " (Nenhum livro cadastrado neste gênero)";
        }

        StringBuilder builder = new StringBuilder();
        for (Livro livro : this.livrosDoGenero) {
            builder.append("\n    - (ID: ").append(livro.getId()).append(") ")
                    .append(livro.getTitulo());
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return (super.toString() + ", Gênero Literário: " + this.genero
                + "\n  Livros do Gênero (" + this.livrosDoGenero.size() + "):"
                + formatarLivrosDoGenero());
    }
}
