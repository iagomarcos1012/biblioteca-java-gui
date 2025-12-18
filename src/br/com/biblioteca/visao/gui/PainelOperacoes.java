package br.com.biblioteca.visao.gui;

import br.com.biblioteca.controlador.BibliotecaController;
import br.com.biblioteca.modelo.Emprestimo;
import br.com.biblioteca.persistencia.Excecao;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PainelOperacoes extends JPanel {

    private final BibliotecaController controller;
    private final JTable tabelaEmprestimosAtivos;
    private final DefaultTableModel tableModel;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PainelOperacoes(BibliotecaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));

        JPanel painelAcoes = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel painelEmprestar = new JPanel(new BorderLayout(5, 5));
        painelEmprestar.setBorder(new TitledBorder("Realizar Empréstimo"));

        JPanel formEmprestar = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField leitorIdField = new JTextField();
        JTextField livroIdField = new JTextField();
        formEmprestar.add(new JLabel("ID do Leitor:"));
        formEmprestar.add(leitorIdField);
        formEmprestar.add(new JLabel("ID do Livro:"));
        formEmprestar.add(livroIdField);

        JButton btnEmprestar = new JButton("Emprestar");
        painelEmprestar.add(formEmprestar, BorderLayout.CENTER);
        painelEmprestar.add(btnEmprestar, BorderLayout.SOUTH);

        JPanel painelDevolver = new JPanel(new BorderLayout(5, 5));
        painelDevolver.setBorder(new TitledBorder("Realizar Devolução"));

        JPanel formDevolver = new JPanel(new GridLayout(1, 2, 5, 5));
        JTextField emprestimoIdField = new JTextField();
        formDevolver.add(new JLabel("ID do Empréstimo:"));
        formDevolver.add(emprestimoIdField);

        JButton btnDevolver = new JButton("Devolver");
        painelDevolver.add(formDevolver, BorderLayout.CENTER);
        painelDevolver.add(btnDevolver, BorderLayout.SOUTH);

        painelAcoes.add(painelEmprestar);
        painelAcoes.add(painelDevolver);

        add(painelAcoes, BorderLayout.NORTH);

        String[] colunas = { "ID Empréstimo", "Leitor", "Livro", "Data Empréstimo", "Data Devolução" };
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEmprestimosAtivos = new JTable(tableModel);
        tabelaEmprestimosAtivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabelaEmprestimosAtivos);
        scrollPane.setBorder(new TitledBorder("Empréstimos Ativos"));

        add(scrollPane, BorderLayout.CENTER);

        JButton btnAtualizarTabela = new JButton("Atualizar Lista de Empréstimos Ativos");
        add(btnAtualizarTabela, BorderLayout.SOUTH);

        btnAtualizarTabela.addActionListener(e -> atualizarTabelaAtivos());

        btnEmprestar.addActionListener(e -> {
            try {
                int leitorId = Integer.parseInt(leitorIdField.getText());
                int livroId = Integer.parseInt(livroIdField.getText());

                Emprestimo emp = controller.realizarEmprestimo(leitorId, livroId);

                JOptionPane.showMessageDialog(this, "Empréstimo (ID: " + emp.getId() + ") realizado com sucesso!");
                leitorIdField.setText("");
                livroIdField.setText("");
                atualizarTabelaAtivos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "IDs devem ser números.", "Erro de Formato",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Excecao ex) {
                JOptionPane.showMessageDialog(this, "Erro ao emprestar: " + ex.getMessage(), "Erro de Regra de Negócio",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDevolver.addActionListener(e -> {
            try {
                int emprestimoId = Integer.parseInt(emprestimoIdField.getText());

                BibliotecaController.InfoDevolucao info = controller.realizarDevolucao(emprestimoId);

                String mensagem = "Devolução realizada com sucesso!\nLivro: " + info.emprestimo.getLivro().getTitulo();

                if (info.multaAplicada > 0) {
                    mensagem += "\n\nATENÇÃO: Devolução com atraso!";
                    mensagem += "\nDias de atraso: " + info.diasDeAtraso;
                    mensagem += String.format("\nMulta aplicada: R$%.2f", info.multaAplicada);
                    mensagem += String.format("\nNovo saldo devedor do leitor: R$%.2f",
                            info.emprestimo.getLeitor().getValorMultaPendente());
                }

                JOptionPane.showMessageDialog(this, mensagem, "Devolução Concluída", JOptionPane.INFORMATION_MESSAGE);
                emprestimoIdField.setText("");
                atualizarTabelaAtivos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID do Empréstimo deve ser um número.", "Erro de Formato",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Excecao ex) {
                JOptionPane.showMessageDialog(this, "Erro ao devolver: " + ex.getMessage(), "Erro de Regra de Negócio",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        atualizarTabelaAtivos();
    }

    private void atualizarTabelaAtivos() {
        tableModel.setRowCount(0);
        List<Emprestimo> ativos = controller.listarEmprestimosAtivos();
        for (Emprestimo emp : ativos) {
            tableModel.addRow(new Object[] {
                    emp.getId(),
                    emp.getLeitor().getNome(),
                    emp.getLivro().getTitulo(),
                    formatter.format(emp.getDataEmprestimo()),
                    formatter.format(emp.getDataPrevistaDevolucao())
            });
        }
    }
}