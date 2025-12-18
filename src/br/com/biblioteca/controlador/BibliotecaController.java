package br.com.biblioteca.controlador;

import br.com.biblioteca.modelo.*;
import br.com.biblioteca.persistencia.BancoDeDados;
import br.com.biblioteca.persistencia.Excecao;
import br.com.biblioteca.util.GeradorDeId;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaController {

    private final BancoDeDados banco;

    private static final double VALOR_MULTA_DIARIA = 0.50;

    public class InfoDevolucao {
        public final Emprestimo emprestimo;
        public final double multaAplicada;
        public final long diasDeAtraso;

        public InfoDevolucao(Emprestimo e, double multa, long dias) {
            this.emprestimo = e;
            this.multaAplicada = multa;
            this.diasDeAtraso = dias;
        }
    }

    public BibliotecaController(BancoDeDados banco) {
        this.banco = banco;
    }

    public Autor cadastrarAutor(String nome, LocalDate dataNasc, String contato, String biografia) throws Excecao {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Excecao("O nome do autor não pode ser vazio.");
        }
        if (dataNasc.isAfter(LocalDate.now())) {
            throw new Excecao("A data de nascimento não pode ser no futuro.");
        }

        int id = GeradorDeId.gerarId("Autor");
        Autor autor = new Autor(biografia, id, nome, dataNasc, contato);
        banco.getAutores().inserirEntidade(autor);
        return autor;
    }

    public Leitor cadastrarLeitor(String nome, LocalDate dataNasc, String contato) throws Excecao {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Excecao("O nome do leitor não pode ser vazio.");
        }
        if (dataNasc.isAfter(LocalDate.now())) {
            throw new Excecao("A data de nascimento não pode ser no futuro.");
        }

        int id = GeradorDeId.gerarId("Leitor");
        double multaPendente = 0.0;
        Leitor leitor = new Leitor(multaPendente, id, nome, dataNasc, contato);
        banco.getLeitores().inserirEntidade(leitor);
        return leitor;
    }

    public Genero cadastrarGenero(String nomeGenero) throws Excecao {
        if (nomeGenero == null || nomeGenero.trim().isEmpty()) {
            throw new Excecao("O nome do gênero não pode ser vazio.");
        }

        int id = GeradorDeId.gerarId("Genero");
        Genero genero = new Genero(id, nomeGenero);
        banco.getGeneros().inserirEntidade(genero);
        return genero;
    }

    public Livro cadastrarLivro(String titulo, int qtdDisponivel, int autorId, int generoId) throws Excecao {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Excecao("O título do livro não pode ser vazio.");
        }
        if (qtdDisponivel < 0) {
            throw new Excecao("A quantidade disponível não pode ser negativa.");
        }

        Autor autor = banco.getAutores().getEntidadeById(autorId);
        Genero genero = banco.getGeneros().getEntidadeById(generoId);

        int id = GeradorDeId.gerarId("Livro");
        Livro livro = new Livro(id, titulo, autor, genero, qtdDisponivel);

        banco.getLivros().inserirEntidade(livro);

        autor.setLivrosEscritos(livro);
        genero.setLivrosDoGenero(livro);

        return livro;
    }

    public List<Autor> listarAutores() {
        return banco.getAutores().listarEntidades();
    }

    public List<Leitor> listarLeitores() {
        return banco.getLeitores().listarEntidades();
    }

    public List<Livro> listarLivros() {
        return banco.getLivros().listarEntidades();
    }

    public List<Genero> listarGeneros() {
        return banco.getGeneros().listarEntidades();
    }

    public List<Emprestimo> listarEmprestimos() {
        return banco.getEmprestimos().listarEntidades();
    }

    public Autor getAutorPorId(int id) throws Excecao {
        return banco.getAutores().getEntidadeById(id);
    }

    public Leitor getLeitorPorId(int id) throws Excecao {
        return banco.getLeitores().getEntidadeById(id);
    }

    public Livro getLivroPorId(int id) throws Excecao {
        return banco.getLivros().getEntidadeById(id);
    }

    public Genero getGeneroPorId(int id) throws Excecao {
        return banco.getGeneros().getEntidadeById(id);
    }

    public Emprestimo getEmprestimoPorId(int id) throws Excecao {
        return banco.getEmprestimos().getEntidadeById(id);
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> ativos = new ArrayList<>();

        List<Emprestimo> todos = banco.getEmprestimos().listarEntidades();

        for (Emprestimo emp : todos) {

            if ("Ativo".equals(emp.getStatus())) {
                ativos.add(emp);
            }
        }
        return ativos;
    }

    public void alterarAutor(int id, String nome, LocalDate dataNasc, String contato, String biografia) throws Excecao {

        Autor autor = banco.getAutores().getEntidadeById(id);

        if (nome == null || nome.trim().isEmpty())
            throw new Excecao("Nome não pode ser vazio.");
        if (dataNasc.isAfter(LocalDate.now()))
            throw new Excecao("Data de nascimento inválida.");

        autor.setNome(nome);
        autor.setDataNasc(dataNasc);
        autor.setContato(contato);
        autor.setBiografia(biografia);

        banco.getAutores().alterarEntidade(autor);
    }

    public void alterarLeitor(int id, String nome, LocalDate dataNasc, String contato) throws Excecao {

        Leitor leitor = banco.getLeitores().getEntidadeById(id);

        if (nome == null || nome.trim().isEmpty())
            throw new Excecao("Nome não pode ser vazio.");
        if (dataNasc.isAfter(LocalDate.now()))
            throw new Excecao("Data de nascimento inválida.");

        leitor.setNome(nome);
        leitor.setDataNasc(dataNasc);
        leitor.setContato(contato);

        banco.getLeitores().alterarEntidade(leitor);
    }

    public void alterarGenero(int id, String nomeGenero) throws Excecao {

        Genero genero = banco.getGeneros().getEntidadeById(id);

        if (nomeGenero == null || nomeGenero.trim().isEmpty())
            throw new Excecao("Nome não pode ser vazio.");

        genero.setGenero(nomeGenero);
        banco.getGeneros().alterarEntidade(genero);
    }

    public void alterarLivro(int id, String titulo, int qtd, int autorId, int generoId) throws Excecao {

        Livro livro = banco.getLivros().getEntidadeById(id);

        if (titulo == null || titulo.trim().isEmpty())
            throw new Excecao("Título não pode ser vazio.");
        if (qtd < 0)
            throw new Excecao("Quantidade não pode ser negativa.");

        Autor novoAutor = banco.getAutores().getEntidadeById(autorId);
        Genero novoGenero = banco.getGeneros().getEntidadeById(generoId);

        Autor autorAntigo = livro.getAutor();
        Genero generoAntigo = livro.getGenero();

        if (autorAntigo != null && !autorAntigo.equals(novoAutor)) {
            autorAntigo.getLivrosEscritos().remove(livro);
            novoAutor.setLivrosEscritos(livro);
        }

        if (generoAntigo != null && !generoAntigo.equals(novoGenero)) {
            generoAntigo.getLivrosDoGenero().remove(livro);
            novoGenero.setLivrosDoGenero(livro);
        }

        livro.setTitulo(titulo);
        livro.setQtdDisponivel(qtd);
        livro.setAutor(novoAutor);
        livro.setGenero(novoGenero);

        banco.getLivros().alterarEntidade(livro);
    }

    public void excluirAutor(int id) throws Excecao {
        Autor autor = banco.getAutores().getEntidadeById(id);
        if (!autor.getLivrosEscritos().isEmpty()) {
            throw new Excecao("Não é possível excluir o autor '" + autor.getNome()
                    + "' pois ele possui livros cadastrados.");
        }
        banco.getAutores().excluirEntidade(id);
    }

    public void excluirGenero(int id) throws Excecao {
        Genero genero = banco.getGeneros().getEntidadeById(id);
        if (!genero.getLivrosDoGenero().isEmpty()) {
            throw new Excecao("Não é possível excluir o gênero '" + genero.getGenero()
                    + "' pois ele possui livros cadastrados.");
        }
        banco.getGeneros().excluirEntidade(id);
    }

    public void excluirLeitor(int id) throws Excecao {
        Leitor leitor = banco.getLeitores().getEntidadeById(id);
        if (leitor.getQuantidadeEmprestimosAtivos() > 0) {
            throw new Excecao("Não é possível excluir o leitor '" + leitor.getNome()
                    + "' pois ele possui empréstimos ativos.");
        }
        banco.getLeitores().excluirEntidade(id);
    }

    public void excluirLivro(int id) throws Excecao {
        Livro livro = banco.getLivros().getEntidadeById(id);
        List<Emprestimo> emprestimosAtivos = listarEmprestimosAtivos();
        for (Emprestimo emp : emprestimosAtivos) {
            if (emp.getLivro().getId() == livro.getId()) {
                throw new Excecao("Não é possível excluir o livro '" + livro.getTitulo()
                        + "' pois ele está em um empréstimo ativo (ID Empréstimo: " + emp.getId() + ").");
            }
        }

        livro.getAutor().getLivrosEscritos().remove(livro);
        livro.getGenero().getLivrosDoGenero().remove(livro);

        banco.getLivros().excluirEntidade(id);
    }

    public Emprestimo realizarEmprestimo(int leitorId, int livroId) throws Excecao {

        Leitor leitor = banco.getLeitores().getEntidadeById(leitorId);
        Livro livro = banco.getLivros().getEntidadeById(livroId);

        if (!leitor.podeEmprestar()) {
            throw new Excecao("O leitor " + leitor.getNome() + " já atingiu o limite de "
                    + Leitor.LIMITE_MAXIMO_LIVROS + " livros.");
        }

        if (leitor.temPendencia()) {
            throw new Excecao("O leitor " + leitor.getNome() + " possui multas pendentes. Valor: R$"
                    + leitor.getValorMultaPendente());
        }

        if (!livro.estaDisponivel()) {
            throw new Excecao("O livro '" + livro.getTitulo() + "' não está disponível (Qtd: 0).");
        }

        int emprestimoId = GeradorDeId.gerarId("Emprestimo");
        Emprestimo novoEmprestimo = new Emprestimo(emprestimoId, leitor, livro);

        livro.diminuirQtd();
        leitor.setHistoricoEmprestimos(novoEmprestimo);

        banco.getEmprestimos().inserirEntidade(novoEmprestimo);
        banco.getLivros().alterarEntidade(livro);

        return novoEmprestimo;
    }

    public InfoDevolucao realizarDevolucao(int emprestimoId) throws Excecao {

        Emprestimo emprestimo = banco.getEmprestimos().getEntidadeById(emprestimoId);

        if ("Finalizado".equals(emprestimo.getStatus())) {
            throw new Excecao("Este empréstimo já foi finalizado anteriormente.");
        }

        Leitor leitor = emprestimo.getLeitor();
        Livro livro = emprestimo.getLivro();
        LocalDate hoje = LocalDate.now();
        LocalDate dataPrevista = emprestimo.getDataPrevistaDevolucao();

        double multaCalculada = 0.0;
        long diasDeAtraso = 0;

        if (hoje.isAfter(dataPrevista)) {
            diasDeAtraso = ChronoUnit.DAYS.between(dataPrevista, hoje);
            multaCalculada = diasDeAtraso * VALOR_MULTA_DIARIA;
            leitor.adicionarMulta(multaCalculada);
        }

        emprestimo.setStatus("Finalizado");
        livro.aumentarQtd();

        banco.getEmprestimos().alterarEntidade(emprestimo);
        banco.getLivros().alterarEntidade(livro);
        banco.getLeitores().alterarEntidade(leitor);

        return new InfoDevolucao(emprestimo, multaCalculada, diasDeAtraso);
    }

    public Leitor quitarMulta(int leitorId, double valorPago) throws Excecao {

        Leitor leitor = banco.getLeitores().getEntidadeById(leitorId);

        if (!leitor.temPendencia()) {
            throw new Excecao("O leitor " + leitor.getNome() + " não possui multas pendentes.");
        }
        if (valorPago <= 0) {
            throw new Excecao("O valor do pagamento deve ser positivo.");
        }

        leitor.quitarMulta(valorPago);
        banco.getLeitores().alterarEntidade(leitor);

        return leitor;
    }
}