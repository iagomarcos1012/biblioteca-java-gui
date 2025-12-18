package br.com.biblioteca.persistencia;

public class Excecao extends Exception {
    public Excecao(String mensagem) {
        super(mensagem);
    }

    public Excecao() {
        super("Ocorreu um erro!");
    }
}