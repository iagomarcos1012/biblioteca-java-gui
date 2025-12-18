package br.com.biblioteca.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Leitor extends Pessoa {
    public static final int LIMITE_MAXIMO_LIVROS = 3;
    private double valorMultaPendente;
    private final List<Emprestimo> historicoEmprestimos;

    public Leitor(double valorMultaPendente, int id, String nome, LocalDate dataNasc, String contato) {
        super(id, nome, dataNasc, contato);
        this.valorMultaPendente = valorMultaPendente;
        this.historicoEmprestimos = new ArrayList<>();
    }

    public double getValorMultaPendente() {
        return this.valorMultaPendente;
    }

    public List<Emprestimo> getHistoricoEmprestimos() {
        return this.historicoEmprestimos;
    }

    public void setValorMultaPendente(double valorMultaPendente) {
        this.valorMultaPendente = valorMultaPendente;
    }

    public void setHistoricoEmprestimos(Emprestimo emprestimo) {
        this.historicoEmprestimos.add(emprestimo);
    }

    public void adicionarMulta(double valorMultaPendente) {
        if (valorMultaPendente > 0)
            this.valorMultaPendente += valorMultaPendente;
    }

    public void quitarMulta(double valorPago) {
        if (valorPago > 0) {
            this.valorMultaPendente -= valorPago;
            if (valorMultaPendente < 0)
                this.valorMultaPendente = 0;
        }
    }

    public boolean temPendencia() {
        return this.valorMultaPendente > 0;
    }

    public int getQuantidadeEmprestimosAtivos() {
        int contador = 0;
        for (Emprestimo emp : this.historicoEmprestimos) {
            if (emp.getStatus().equals("Ativo")) {
                contador++;
            }
        }
        return contador;
    }

    public boolean podeEmprestar() {
        return getQuantidadeEmprestimosAtivos() < LIMITE_MAXIMO_LIVROS;
    }

    private String formatarLivrosEmprestadosAtivos() {
        StringBuilder builder = new StringBuilder();
        boolean encontrouLivro = false;

        for (Emprestimo emp : this.historicoEmprestimos) {
            if (emp.getStatus().equals("Ativo")) {
                builder.append("\n    - (Empréstimo ID: ").append(emp.getId()).append(") ")
                        .append(emp.getLivro().getTitulo())
                        .append(" | Devolver até: ").append(emp.getDataPrevistaDevolucao());
                encontrouLivro = true;
            }
        }

        if (!encontrouLivro) {
            return " (Nenhum livro emprestado no momento)";
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        String infoPessoa = super.toString();
        String infoLeitor = String.format("Multa Pendente: R$%.2f", this.valorMultaPendente);

        return (infoPessoa + ", " + infoLeitor
                + "\n  Empréstimos Ativos (" + getQuantidadeEmprestimosAtivos() + "/" + LIMITE_MAXIMO_LIVROS + "):"
                + formatarLivrosEmprestadosAtivos());
    }
}
