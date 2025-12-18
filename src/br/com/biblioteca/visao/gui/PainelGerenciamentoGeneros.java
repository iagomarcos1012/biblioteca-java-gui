package br.com.biblioteca.visao.gui;

import br.com.biblioteca.controlador.BibliotecaController;
import br.com.biblioteca.modelo.Genero;
import br.com.biblioteca.persistencia.Excecao;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PainelGerenciamentoGeneros extends JPanel {

    private final BibliotecaController controller;
    private final JTable tabela;
    private final DefaultTableModel tableModel;

    public PainelGerenciamentoGeneros(BibliotecaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));

        String[] colunas = { "ID", "Nome do Gênero", "Qtd. Livros" };
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(tableModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

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

        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnAdicionar.addActionListener(e -> adicionarGenero());
        btnExcluir.addActionListener(e -> excluirGenero());
        btnEditar.addActionListener(e -> editarGenero());

        atualizarTabela();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Genero> generos = controller.listarGeneros();
        for (Genero genero : generos) {
            tableModel.addRow(new Object[] {
                    genero.getId(),
                    genero.getGenero(),
                    genero.getLivrosDoGenero().size()
            });
        }
    }

    private void adicionarGenero() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do Gênero:", "Adicionar Gênero",
                JOptionPane.PLAIN_MESSAGE);

        if (nome != null && !nome.trim().isEmpty()) {
            try {
                controller.cadastrarGenero(nome);
                JOptionPane.showMessageDialog(this, "Gênero cadastrado com sucesso!");
                atualizarTabela();
            } catch (Excecao ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarGenero() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um gênero na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nomeAtual = (String) tableModel.getValueAt(selectedRow, 1);

        String nomeNovo = JOptionPane.showInputDialog(this, "Digite o novo nome para o Gênero:",
                "Editar Gênero (ID: " + id + ")", JOptionPane.PLAIN_MESSAGE, null, null, nomeAtual).toString();

        if (nomeNovo != null && !nomeNovo.trim().isEmpty()) {
            try {
                controller.alterarGenero(id, nomeNovo);
                JOptionPane.showMessageDialog(this, "Gênero alterado com sucesso!");
                atualizarTabela();
            } catch (Excecao ex) {
                JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void excluirGenero() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um gênero na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nome = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o gênero: " + nome + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.excluirGenero(id);
                JOptionPane.showMessageDialog(this, "Gênero excluído com sucesso!");
                atualizarTabela();
            } catch (Excecao ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro de Regra de Negócio",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}