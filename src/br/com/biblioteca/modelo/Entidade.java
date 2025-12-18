package br.com.biblioteca.modelo;

public abstract class Entidade {
    protected int id;

    public Entidade(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ("ID: " + this.id);
    }
}
