package br.com.biblioteca.visao.gui;

import br.com.biblioteca.controlador.BibliotecaController;
import br.com.biblioteca.modelo.Autor;
import br.com.biblioteca.persistencia.Excecao;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PainelGerenciamentoAutores extends JPanel {

    private final BibliotecaController controller;
    private final JTable tabela;
    private final DefaultTableModel tableModel;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PainelGerenciamentoAutores(BibliotecaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));

        String[] colunas = { "ID", "Nome", "Nascimento", "Contato", "Biografia" };
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
        JButton btnEditar = new JButton("Editar Selecionado");
        JButton btnExcluir = new JButton("Excluir Selecionado");
        JButton btnAtualizar = new JButton("Atualizar Tabela");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);

        add(painelBotoes, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnAdicionar.addActionListener(e -> adicionarAutor());
        btnExcluir.addActionListener(e -> excluirAutor());
        btnEditar.addActionListener(e -> editarAutor());

        atualizarTabela();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Autor> autores = controller.listarAutores();
        for (Autor autor : autores) {
            tableModel.addRow(new Object[] {
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNasc() != null ? formatter.format(autor.getDataNasc()) : "N/A",
                    autor.getContato(),
                    autor.getBiografia()
            });
        }
    }

    private void adicionarAutor() {

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JTextField nomeField = new JTextField(25);
        JTextField dataNascField = new JTextField("dd/mm/aaaa", 25);
        JTextField contatoField = new JTextField(25);
        JTextArea biografiaArea = new JTextArea(5, 25);

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Data Nascimento (dd/mm/aaaa):"));
        formPanel.add(dataNascField);
        formPanel.add(new JLabel("Contato:"));
        formPanel.add(contatoField);
        formPanel.add(new JLabel("Biografia:"));
        formPanel.add(new JScrollPane(biografiaArea));

        int result = JOptionPane.showConfirmDialog(this, formPanel,
                "Adicionar Novo Autor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nome = nomeField.getText();
                LocalDate dataNasc = LocalDate.parse(dataNascField.getText(), formatter);
                String contato = contatoField.getText();
                String biografia = biografiaArea.getText();

                controller.cadastrarAutor(nome, dataNasc, contato, biografia);

                JOptionPane.showMessageDialog(this, "Autor cadastrado com sucesso!");
                atualizarTabela();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Erro no formato da data. Use dd/mm/aaaa.", "Erro de Formato",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Excecao ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarAutor() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um autor na tabela para editar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Autor autor = controller.getAutorPorId(id);

            JTextField nomeField = new JTextField(autor.getNome(), 25);
            JTextField dataNascField = new JTextField(formatter.format(autor.getDataNasc()), 25);
            JTextField contatoField = new JTextField(autor.getContato(), 25);
            JTextArea biografiaArea = new JTextArea(autor.getBiografia(), 5, 25);

            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
            formPanel.add(new JLabel("Nome:"));
            formPanel.add(nomeField);
            formPanel.add(new JLabel("Data Nascimento (dd/mm/aaaa):"));
            formPanel.add(dataNascField);
            formPanel.add(new JLabel("Contato:"));
            formPanel.add(contatoField);
            formPanel.add(new JLabel("Biografia:"));
            formPanel.add(new JScrollPane(biografiaArea));

            int result = JOptionPane.showConfirmDialog(this, formPanel,
                    "Editar Autor (ID: " + id + ")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nome = nomeField.getText();
                LocalDate dataNasc = LocalDate.parse(dataNascField.getText(), formatter);
                String contato = contatoField.getText();
                String biografia = biografiaArea.getText();

                controller.alterarAutor(id, nome, dataNasc, contato, biografia);

                JOptionPane.showMessageDialog(this, "Autor alterado com sucesso!");
                atualizarTabela();
            }
        } catch (Excecao | DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar autor: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirAutor() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um autor na tabela para excluir.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nome = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o autor: " + nome + " (ID: " + id + ")?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {

                controller.excluirAutor(id);
                JOptionPane.showMessageDialog(this, "Autor excluído com sucesso!");
                atualizarTabela();
            } catch (Excecao ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro de Regra de Negócio",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}