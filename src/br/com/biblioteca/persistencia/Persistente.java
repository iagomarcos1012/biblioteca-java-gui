package br.com.biblioteca.persistencia;

import br.com.biblioteca.modelo.Entidade;
import java.util.ArrayList;
import java.util.List;

public class Persistente<T extends Entidade> {
    private final List<T> entidades;

    public Persistente() {
        this.entidades = new ArrayList<>();
    }

    public void inserirEntidade(T entidade) throws Excecao {
        for (T e : entidades) {
            if (e.getId() == entidade.getId()) {
                throw new Excecao("Já existe uma entidade do tipo " +
                        entidade.getClass().getSimpleName() +
                        " com o ID " + entidade.getId());
            }
        }
        this.entidades.add(entidade);
    }

    public void alterarEntidade(T entidade) throws Excecao {
        for (int i = 0; i < this.entidades.size(); i++) {
            if (this.entidades.get(i).getId() == entidade.getId()) {
                this.entidades.set(i, entidade);
                return;
            }
        }
        throw new Excecao("Não foi possível alterar: Entidade com ID " +
                entidade.getId() + " não encontrada.");
    }

    public void excluirEntidade(int id) throws Excecao {
        boolean removido = this.entidades.removeIf(entidade -> entidade.getId() == id);
        if (!removido) {
            throw new Excecao("Não foi possível excluir: Entidade com ID " + id + " não encontrada.");
        }
    }

    public T getEntidadeById(int id) throws Excecao {
        for (T entidade : this.entidades) {
            if (entidade.getId() == id) {
                return entidade;
            }
        }
        throw new Excecao("Não foi possível encontrar uma entidade com o ID: " + id);
    }

    public List<T> listarEntidades() {
        return new ArrayList<>(this.entidades);
    }

    @Override
    public String toString() {
        if (this.entidades.isEmpty()) {
            return ("Nenhuma entidade cadastrada nesta lista.");
        }
        StringBuilder builder = new StringBuilder();
        for (T entidade : entidades) {
            builder.append(entidade.toString()).append("\n");
        }
        return builder.toString();
    }
}