package br.com.biblioteca.modelo;

import java.time.LocalDate;

public abstract class Pessoa extends Entidade {
    protected String nome;
    protected String contato;
    protected LocalDate dataNasc;

    public Pessoa(int id, String nome, LocalDate dataNasc, String contato) {
        super(id);
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.contato = contato;
    }

    public String getNome() {
        return this.nome;
    }

    public String getContato() {
        return this.contato;
    }

    public LocalDate getDataNasc() {
        return this.dataNasc;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    @Override
    public String toString() {
        return (super.toString() + ", Nome: " + this.nome + ", Contato: " + this.contato + ", Data de Nascimento: "
                + this.dataNasc);
    }
}
