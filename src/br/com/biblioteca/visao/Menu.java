package br.com.biblioteca.visao;

import br.com.biblioteca.controlador.BibliotecaController;
import br.com.biblioteca.modelo.*;
import br.com.biblioteca.persistencia.Excecao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final BibliotecaController controller;
    private final Scanner scanner;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Menu(BibliotecaController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n--- Sistema de Gerenciamento da Biblioteca ---");
            System.out.println("1. Gerenciar Livros");
            System.out.println("2. Gerenciar Autores");
            System.out.println("3. Gerenciar Gêneros");
            System.out.println("4. Gerenciar Leitores");
            System.out.println("5. Gerenciar Empréstimos");
            System.out.println("0. Sair");

            opcao = lerOpcao("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> menuLivros();
                case 2 -> menuAutores();
                case 3 -> menuGeneros();
                case 4 -> menuLeitores();
                case 5 -> menuEmprestimos();
                case 0 -> System.out.println("Saindo do sistema. Até logo!");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void menuLivros() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Livros ---");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Alterar Livro");
            System.out.println("3. Excluir Livro");
            System.out.println("4. Visualizar Livro por ID");
            System.out.println("5. Visualizar Todos os Livros");
            System.out.println("0. Voltar ao Menu Principal");

            opcao = lerOpcao("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> inserirLivro();
                case 2 -> alterarLivro();
                case 3 -> excluirLivro();
                case 4 -> visualizarLivroPorId();
                case 5 -> visualizarTodosLivros();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuAutores() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Autores ---");
            System.out.println("1. Cadastrar Autor");
            System.out.println("2. Alterar Autor");
            System.out.println("3. Excluir Autor");
            System.out.println("4. Visualizar Autor por ID");
            System.out.println("5. Visualizar Todos os Autores");
            System.out.println("0. Voltar ao Menu Principal");

            opcao = lerOpcao("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> inserirAutor();
                case 2 -> alterarAutor();
                case 3 -> excluirAutor();
                case 4 -> visualizarAutorPorId();
                case 5 -> visualizarTodosAutores();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuGeneros() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Gêneros ---");
            System.out.println("1. Cadastrar Gênero");
            System.out.println("2. Alterar Gênero");
            System.out.println("3. Excluir Gênero");
            System.out.println("4. Visualizar Gênero por ID");
            System.out.println("5. Visualizar Todos os Gêneros");
            System.out.println("0. Voltar ao Menu Principal");

            opcao = lerOpcao("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> inserirGenero();
                case 2 -> alterarGenero();
                case 3 -> excluirGenero();
                case 4 -> visualizarGeneroPorId();
                case 5 -> visualizarTodosGeneros();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuLeitores() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Leitores ---");
            System.out.println("1. Cadastrar Leitor");
            System.out.println("2. Alterar Leitor");
            System.out.println("3. Excluir Leitor");
            System.out.println("4. Visualizar Leitor por ID");
            System.out.println("5. Visualizar Todos os Leitores");
            System.out.println("6. Quitar Multa de Leitor");
            System.out.println("0. Voltar ao Menu Principal");

            opcao = lerOpcao("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> inserirLeitor();
                case 2 -> alterarLeitor();
                case 3 -> excluirLeitor();
                case 4 -> visualizarLeitorPorId();
                case 5 -> visualizarTodosLeitores();
                case 6 -> quitarMultaLeitor();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuEmprestimos() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Empréstimos ---");
            System.out.println("1. Realizar Novo Empréstimo");
            System.out.println("2. Devolver Livro");
            System.out.println("3. Visualizar Empréstimo por ID");
            System.out.println("4. Visualizar Empréstimos Ativos");
            System.out.println("0. Voltar ao Menu Principal");

            opcao = lerOpcao("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> inserirEmprestimo();
                case 2 -> devolverEmprestimo();
                case 3 -> visualizarEmprestimoPorId();
                case 4 -> visualizarEmprestimosAtivos();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void inserirAutor() {
        System.out.println("\n--- Cadastrar Autor ---");
        try {
            String nome = lerString("Nome: ");
            LocalDate dataNasc = lerData("Data de Nascimento (dd/mm/aaaa): ");
            String contato = lerString("Contato (Telefone/Email): ");
            String biografia = lerString("Biografia: ");

            Autor autor = controller.cadastrarAutor(nome, dataNasc, contato, biografia);
            System.out.println("Autor cadastrado com sucesso! ID: " + autor.getId());

        } catch (Excecao e) {
            System.out.println("ERRO ao cadastrar autor: " + e.getMessage());
        }
    }

    private void inserirLeitor() {
        System.out.println("\n--- Cadastrar Leitor ---");
        try {
            String nome = lerString("Nome: ");
            LocalDate dataNasc = lerData("Data de Nascimento (dd/mm/aaaa): ");
            String contato = lerString("Contato (Telefone/Email): ");

            Leitor leitor = controller.cadastrarLeitor(nome, dataNasc, contato);
            System.out.println("Leitor cadastrado com sucesso! ID: " + leitor.getId());

        } catch (Excecao e) {
            System.out.println("ERRO ao cadastrar leitor: " + e.getMessage());
        }
    }

    private void inserirGenero() {
        System.out.println("\n--- Cadastrar Gênero ---");
        try {
            String nomeGenero = lerString("Nome do Gênero: ");
            Genero genero = controller.cadastrarGenero(nomeGenero);
            System.out.println("Gênero cadastrado com sucesso! ID: " + genero.getId());

        } catch (Excecao e) {
            System.out.println("ERRO ao cadastrar gênero: " + e.getMessage());
        }
    }

    private void inserirLivro() {
        System.out.println("\n--- Cadastrar Livro ---");
        try {
            String titulo = lerString("Título: ");
            int qtd = lerOpcao("Quantidade Disponível: ");
            int autorId = lerOpcao("Digite o ID do Autor: ");
            int generoId = lerOpcao("Digite o ID do Gênero: ");

            Livro livro = controller.cadastrarLivro(titulo, qtd, autorId, generoId);
            System.out.println("Livro cadastrado com sucesso! ID: " + livro.getId());

        } catch (Excecao e) {
            System.out.println("ERRO ao cadastrar livro: " + e.getMessage());
        }
    }

    private void visualizarAutorPorId() {
        System.out.println("\n--- Visualizar Autor por ID ---");
        int id = lerOpcao("Digite o ID do Autor a ser visualizado: ");
        try {
            Autor autor = controller.getAutorPorId(id);
            System.out.println(autor.toString());
        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void visualizarLeitorPorId() {
        System.out.println("\n--- Visualizar Leitor por ID ---");
        int id = lerOpcao("Digite o ID do Leitor a ser visualizado: ");
        try {
            Leitor leitor = controller.getLeitorPorId(id);
            System.out.println(leitor.toString());
        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void visualizarGeneroPorId() {
        System.out.println("\n--- Visualizar Gênero por ID ---");
        int id = lerOpcao("Digite o ID do Gênero a ser visualizado: ");
        try {
            Genero genero = controller.getGeneroPorId(id);
            System.out.println(genero.toString());
        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void visualizarLivroPorId() {
        System.out.println("\n--- Visualizar Livro por ID ---");
        int id = lerOpcao("Digite o ID do Livro a ser visualizado: ");
        try {
            Livro livro = controller.getLivroPorId(id);
            System.out.println(livro.toString());
        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void visualizarEmprestimoPorId() {
        System.out.println("\n--- Visualizar Empréstimo por ID ---");
        int id = lerOpcao("Digite o ID do Empréstimo a ser visualizado: ");
        try {
            Emprestimo emprestimo = controller.getEmprestimoPorId(id);
            System.out.println(emprestimo.toString());
        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void visualizarTodosAutores() {
        System.out.println("\n--- Visualizar Todos os Autores ---");
        List<Autor> autores = controller.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
            return;
        }
        for (Autor autor : autores) {
            System.out.println("--------------------");
            System.out.println(autor.toString());
        }
    }

    private void visualizarTodosLeitores() {
        System.out.println("\n--- Visualizar Todos os Leitores ---");
        List<Leitor> leitores = controller.listarLeitores();
        if (leitores.isEmpty()) {
            System.out.println("Nenhum leitor cadastrado.");
            return;
        }
        for (Leitor leitor : leitores) {
            System.out.println("--------------------");
            System.out.println(leitor.toString());
        }
    }

    private void visualizarTodosGeneros() {
        System.out.println("\n--- Visualizar Todos os Gêneros ---");
        List<Genero> generos = controller.listarGeneros();
        if (generos.isEmpty()) {
            System.out.println("Nenhum gênero cadastrado.");
            return;
        }
        for (Genero genero : generos) {
            System.out.println("--------------------");
            System.out.println(genero.toString());
        }
    }

    private void visualizarTodosLivros() {
        System.out.println("\n--- Visualizar Todos os Livros ---");
        List<Livro> livros = controller.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        for (Livro livro : livros) {
            System.out.println("--------------------");
            System.out.println(livro.toString());
        }
    }

    private void alterarAutor() {
        System.out.println("\n--- Alterar Autor ---");
        int id = lerOpcao("Digite o ID do Autor a ser alterado: ");
        try {
            Autor autor = controller.getAutorPorId(id);
            System.out.println("Dados atuais: " + autor.toString());
            System.out.println("(Pressione Enter para manter o valor atual)");

            String nome = lerStringOpcional("Novo Nome (" + autor.getNome() + "): ", autor.getNome());
            LocalDate dataNasc = lerDataOpcional(
                    "Nova Data de Nascimento ("
                            + (autor.getDataNasc() != null ? formatter.format(autor.getDataNasc()) : "N/A") + "): ",
                    autor.getDataNasc());
            String contato = lerStringOpcional("Novo Contato (" + autor.getContato() + "): ", autor.getContato());
            String biografia = lerStringOpcional("Nova Biografia (" + autor.getBiografia() + "): ",
                    autor.getBiografia());

            controller.alterarAutor(id, nome, dataNasc, contato, biografia);
            System.out.println("Autor alterado com sucesso!");

        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void alterarLeitor() {
        System.out.println("\n--- Alterar Leitor ---");
        int id = lerOpcao("Digite o ID do Leitor a ser alterado: ");
        try {
            Leitor leitor = controller.getLeitorPorId(id);
            System.out.println("Dados atuais: " + leitor.toString());
            System.out.println("(Pressione Enter para manter o valor atual)");

            String nome = lerStringOpcional("Novo Nome (" + leitor.getNome() + "): ", leitor.getNome());
            LocalDate dataNasc = lerDataOpcional(
                    "Nova Data de Nascimento ("
                            + (leitor.getDataNasc() != null ? formatter.format(leitor.getDataNasc()) : "N/A") + "): ",
                    leitor.getDataNasc());
            String contato = lerStringOpcional("Novo Contato (" + leitor.getContato() + "): ", leitor.getContato());

            controller.alterarLeitor(id, nome, dataNasc, contato);
            System.out.println("Leitor alterado com sucesso!");

        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void alterarGenero() {
        System.out.println("\n--- Alterar Gênero ---");
        int id = lerOpcao("Digite o ID do Gênero a ser alterado: ");
        try {
            Genero genero = controller.getGeneroPorId(id);
            System.out.println("Dados atuais: " + genero.toString());
            System.out.println("(Pressione Enter para manter o valor atual)");

            String nome = lerStringOpcional("Novo Nome (" + genero.getGenero() + "): ", genero.getGenero());

            controller.alterarGenero(id, nome);
            System.out.println("Gênero alterado com sucesso!");

        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void alterarLivro() {
        System.out.println("\n--- Alterar Livro ---");
        int id = lerOpcao("Digite o ID do Livro a ser alterado: ");
        try {
            Livro livro = controller.getLivroPorId(id);
            System.out.println("Dados atuais: " + livro.toString());
            System.out.println("(Pressione Enter para manter o valor atual)");

            String titulo = lerStringOpcional("Novo Título (" + livro.getTitulo() + "): ", livro.getTitulo());
            int qtd = lerOpcaoOpcional("Nova Quantidade (" + livro.getQtdDisponivel() + "): ",
                    livro.getQtdDisponivel());
            int autorId = lerOpcaoOpcional("Novo ID do Autor (" + livro.getAutor().getId() + "): ",
                    livro.getAutor().getId());
            int generoId = lerOpcaoOpcional("Novo ID do Gênero (" + livro.getGenero().getId() + "): ",
                    livro.getGenero().getId());

            controller.alterarLivro(id, titulo, qtd, autorId, generoId);
            System.out.println("Livro alterado com sucesso!");

        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void excluirAutor() {
        System.out.println("\n--- Excluir Autor ---");
        int id = lerOpcao("Digite o ID do Autor a ser excluído: ");
        try {
            controller.excluirAutor(id);
            System.out.println("Autor excluído com sucesso!");
        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void excluirLeitor() {
        System.out.println("\n--- Excluir Leitor ---");
        int id = lerOpcao("Digite o ID do Leitor a ser excluído: ");
        try {
            controller.excluirLeitor(id);
            System.out.println("Leitor excluído com sucesso!");
        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void excluirGenero() {
        System.out.println("\n--- Excluir Gênero ---");
        int id = lerOpcao("Digite o ID do Gênero a ser excluído: ");
        try {
            controller.excluirGenero(id);
            System.out.println("Gênero excluído com sucesso!");
        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void excluirLivro() {
        System.out.println("\n--- Excluir Livro ---");
        int id = lerOpcao("Digite o ID do Livro a ser excluído: ");
        try {
            controller.excluirLivro(id);
            System.out.println("Livro excluído com sucesso!");
        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void inserirEmprestimo() {
        System.out.println("\n--- Realizar Novo Empréstimo ---");
        try {
            int leitorId = lerOpcao("Digite o ID do Leitor: ");
            int livroId = lerOpcao("Digite o ID do Livro: ");

            Emprestimo novoEmprestimo = controller.realizarEmprestimo(leitorId, livroId);

            System.out.println("Empréstimo realizado com sucesso!");
            System.out.println(novoEmprestimo.toString());

        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void devolverEmprestimo() {
        System.out.println("\n--- Devolver Livro ---");
        int emprestimoId = lerOpcao("Digite o ID do Empréstimo a ser finalizado: ");

        try {

            BibliotecaController.InfoDevolucao info = controller.realizarDevolucao(emprestimoId);

            if (info.multaAplicada > 0) {
                System.out.println("---------------------------------");
                System.out.println("ATENÇÃO: Devolução com atraso!");
                System.out.println("Dias de atraso: " + info.diasDeAtraso);
                System.out.printf("Multa aplicada: R$%.2f\n", info.multaAplicada);
                System.out.printf("Novo saldo devedor do leitor: R$%.2f\n",
                        info.emprestimo.getLeitor().getValorMultaPendente());
                System.out.println("---------------------------------");
            } else {
                System.out.println("Devolução realizada dentro do prazo.");
            }
            System.out.println("Livro '" + info.emprestimo.getLivro().getTitulo() + "' devolvido com sucesso!");

        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void quitarMultaLeitor() {
        System.out.println("\n--- Quitar Multa de Leitor ---");
        int leitorId = lerOpcao("Digite o ID do Leitor: ");
        try {
            Leitor leitor = controller.getLeitorPorId(leitorId);

            if (!leitor.temPendencia()) {
                System.out.println("O leitor " + leitor.getNome() + " não possui multas pendentes.");
                return;
            }

            System.out.printf("Leitor: %s\n", leitor.getNome());
            System.out.printf("Valor atual da multa: R$%.2f\n", leitor.getValorMultaPendente());

            double valorPago = lerDouble("Digite o valor a ser pago: ");

            Leitor leitorAtualizado = controller.quitarMulta(leitorId, valorPago);

            System.out.println("Pagamento registrado com sucesso.");
            System.out.printf("Novo saldo devedor: R$%.2f\n", leitorAtualizado.getValorMultaPendente());

        } catch (Excecao e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void visualizarEmprestimosAtivos() {
        System.out.println("\n--- Visualizar Empréstimos Ativos ---");

        List<Emprestimo> ativos = controller.listarEmprestimosAtivos();

        if (ativos.isEmpty()) {
            System.out.println("Nenhum empréstimo ativo no momento.");
            return;
        }

        for (Emprestimo emprestimo : ativos) {
            System.out.println("--------------------");
            System.out.println(emprestimo.toString());
        }
    }

    private int lerOpcao(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int opcao = scanner.nextInt();
                scanner.nextLine();
                return opcao;
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Digite apenas números inteiros.");
                scanner.nextLine();
            }
        }
    }

    private double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                double valor = scanner.nextDouble();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Digite um valor numérico válido (ex: 10.50).");
                scanner.nextLine();
            }
        }
    }

    private String lerString(String mensagem) {
        String entrada;
        while (true) {
            System.out.print(mensagem);
            entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("ERRO: Este campo não pode ficar vazio.");
        }
    }

    private LocalDate lerData(String mensagem) {
        while (true) {
            try {
                String dataStr = lerString(mensagem);
                return LocalDate.parse(dataStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("ERRO: Formato de data inválido. Use dd/mm/aaaa.");
            }
        }
    }

    private String lerStringOpcional(String mensagem, String valorAtual) {
        System.out.print(mensagem);
        String entrada = scanner.nextLine().trim();
        return entrada.isEmpty() ? valorAtual : entrada;
    }

    private int lerOpcaoOpcional(String mensagem, int valorAtual) {
        while (true) {
            try {
                System.out.print(mensagem);
                String entrada = scanner.nextLine().trim();
                if (entrada.isEmpty()) {
                    return valorAtual;
                }
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Digite apenas números inteiros.");
            }
        }
    }

    private LocalDate lerDataOpcional(String mensagem, LocalDate valorAtual) {
        while (true) {
            try {
                String valorFormatado = (valorAtual != null) ? formatter.format(valorAtual) : "N/A";
                System.out.print(mensagem + valorFormatado + "): ");

                String dataStr = scanner.nextLine().trim();
                if (dataStr.isEmpty()) {
                    return valorAtual;
                }
                return LocalDate.parse(dataStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("ERRO: Formato de data inválido. Use dd/mm/aaaa.");
            }
        }
    }
}