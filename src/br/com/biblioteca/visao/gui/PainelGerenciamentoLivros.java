package br.com.biblioteca.visao.gui;

import br.com.biblioteca.controlador.BibliotecaController;
import br.com.biblioteca.modelo.Livro;
import br.com.biblioteca.persistencia.Excecao;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PainelGerenciamentoLivros extends JPanel {

    private final BibliotecaController controller;
    private final JTable tabela;
    private final DefaultTableModel tableModel;

    public PainelGerenciamentoLivros(BibliotecaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));

        // --- 1. Tabela (Centro) ---
        String[] colunas = { "ID", "Título", "Autor", "Gênero", "Qtd. Disponível" };
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(tableModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // --- 2. Painel de Botões (Sul) ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnAtualizar = new JButton("Atualizar Tabela");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);

        add(painelBotoes, BorderLayout.SOUTH);

        // --- 3. Ações dos Botões ---
        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnAdicionar.addActionListener(e -> adicionarLivro());
        btnExcluir.addActionListener(e -> excluirLivro());
        btnEditar.addActionListener(e -> editarLivro());

        // --- 4. Carregar dados iniciais ---
        atualizarTabela();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Livro> livros = controller.listarLivros();
        for (Livro livro : livros) {
            tableModel.addRow(new Object[] {
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor() != null ? livro.getAutor().getNome() : "N/A",
                    livro.getGenero() != null ? livro.getGenero().getGenero() : "N/A",
                    livro.getQtdDisponivel()
            });
        }
    }

    // Este método cria o painel de formulário para Adicionar e Editar
    private JPanel criarFormularioLivro(JTextField tituloField, JTextField qtdField, JTextField autorIdField,
            JTextField generoIdField) {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5)); // 4 linhas, 2 colunas
        formPanel.add(new JLabel("Título:"));
        formPanel.add(tituloField);
        formPanel.add(new JLabel("Qtd. Disponível:"));
        formPanel.add(qtdField);
        formPanel.add(new JLabel("ID do Autor:"));
        formPanel.add(autorIdField);
        formPanel.add(new JLabel("ID do Gênero:"));
        formPanel.add(generoIdField);
        return formPanel;
    }

    private void adicionarLivro() {
        JTextField tituloField = new JTextField();
        JTextField qtdField = new JTextField();
        JTextField autorIdField = new JTextField();
        JTextField generoIdField = new JTextField();

        JPanel formPanel = criarFormularioLivro(tituloField, qtdField, autorIdField, generoIdField);

        int result = JOptionPane.showConfirmDialog(this, formPanel,
                "Adicionar Novo Livro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String titulo = tituloField.getText();
                int qtd = Integer.parseInt(qtdField.getText());
                int autorId = Integer.parseInt(autorIdField.getText());
                int generoId = Integer.parseInt(generoIdField.getText());

                controller.cadastrarLivro(titulo, qtd, autorId, generoId);

                JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
                atualizarTabela();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID e Quantidade devem ser números.", "Erro de Formato",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Excecao ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarLivro() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Livro livro = controller.getLivroPorId(id);

            JTextField tituloField = new JTextField(livro.getTitulo());
            JTextField qtdField = new JTextField(String.valueOf(livro.getQtdDisponivel()));
            JTextField autorIdField = new JTextField(String.valueOf(livro.getAutor().getId()));
            JTextField generoIdField = new JTextField(String.valueOf(livro.getGenero().getId()));

            JPanel formPanel = criarFormularioLivro(tituloField, qtdField, autorIdField, generoIdField);

            int result = JOptionPane.showConfirmDialog(this, formPanel,
                    "Editar Livro (ID: " + id + ")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String titulo = tituloField.getText();
                int qtd = Integer.parseInt(qtdField.getText());
                int autorId = Integer.parseInt(autorIdField.getText());
                int generoId = Integer.parseInt(generoIdField.getText());

                controller.alterarLivro(id, titulo, qtd, autorId, generoId);
                JOptionPane.showMessageDialog(this, "Livro alterado com sucesso!");
                atualizarTabela();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID e Quantidade devem ser números.", "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Excecao ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar livro: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirLivro() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nome = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o livro: " + nome + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.excluirLivro(id);
                JOptionPane.showMessageDialog(this, "Livro excluído com sucesso!");
                atualizarTabela();
            } catch (Excecao ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro de Regra de Negócio",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}