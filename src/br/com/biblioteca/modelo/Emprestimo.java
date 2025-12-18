package br.com.biblioteca.modelo;

import java.time.LocalDate;

public class Emprestimo extends Entidade {

    private final Leitor leitor;
    private final Livro livro;

    private final LocalDate dataEmprestimo;
    private final LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucaoReal;
    private String status;

    public Emprestimo(int id, Leitor leitor, Livro livro) {
        super(id);
        this.leitor = leitor;
        this.livro = livro;
        this.dataEmprestimo = LocalDate.now();
        this.dataPrevistaDevolucao = this.dataEmprestimo.plusDays(7);
        this.status = "Ativo";
        this.dataDevolucaoReal = null;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public Livro getLivro() {
        return livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void finalizarEmprestimo() {
        this.status = "Finalizado";
        this.dataDevolucaoReal = LocalDate.now();
        this.livro.aumentarQtd();
    }

    @Override
    public String toString() {
        return (super.toString() + ", Status: " + this.status
                + ", Leitor: " + this.leitor.getNome()
                + "\n  Livro: " + this.livro.getTitulo()
                + "\n  Data Empréstimo: " + this.dataEmprestimo
                + ", Data Devolução: " + this.dataPrevistaDevolucao);
    }
}
