package br.com.biblioteca.visao.gui;

import br.com.biblioteca.controlador.BibliotecaController;
import br.com.biblioteca.modelo.Leitor;
import br.com.biblioteca.persistencia.Excecao;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PainelGerenciamentoLeitores extends JPanel {

    private final BibliotecaController controller;
    private final JTable tabela;
    private final DefaultTableModel tableModel;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PainelGerenciamentoLeitores(BibliotecaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));

        String[] colunas = { "ID", "Nome", "Nascimento", "Contato", "Multa Pendente (R$)", "Livros Ativos" };
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
        JButton btnQuitarMulta = new JButton("Quitar Multa");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnQuitarMulta);

        add(painelBotoes, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnAdicionar.addActionListener(e -> adicionarLeitor());
        btnExcluir.addActionListener(e -> excluirLeitor());
        btnEditar.addActionListener(e -> editarLeitor());
        btnQuitarMulta.addActionListener(e -> quitarMulta());

        atualizarTabela();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Leitor> leitores = controller.listarLeitores();
        for (Leitor leitor : leitores) {
            tableModel.addRow(new Object[] {
                    leitor.getId(),
                    leitor.getNome(),
                    leitor.getDataNasc() != null ? formatter.format(leitor.getDataNasc()) : "N/A",
                    leitor.getContato(),
                    String.format("%.2f", leitor.getValorMultaPendente()),
                    leitor.getQuantidadeEmprestimosAtivos() + " / " + Leitor.LIMITE_MAXIMO_LIVROS
            });
        }
    }

    private void adicionarLeitor() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JTextField nomeField = new JTextField(25);
        JTextField dataNascField = new JTextField("dd/mm/aaaa", 25);
        JTextField contatoField = new JTextField(25);

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Data Nascimento (dd/mm/aaaa):"));
        formPanel.add(dataNascField);
        formPanel.add(new JLabel("Contato:"));
        formPanel.add(contatoField);

        int result = JOptionPane.showConfirmDialog(this, formPanel,
                "Adicionar Novo Leitor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nome = nomeField.getText();
                LocalDate dataNasc = LocalDate.parse(dataNascField.getText(), formatter);
                String contato = contatoField.getText();

                controller.cadastrarLeitor(nome, dataNasc, contato);
                JOptionPane.showMessageDialog(this, "Leitor cadastrado com sucesso!");
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

    private void editarLeitor() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um leitor na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Leitor leitor = controller.getLeitorPorId(id);

            JTextField nomeField = new JTextField(leitor.getNome(), 25);
            JTextField dataNascField = new JTextField(formatter.format(leitor.getDataNasc()), 25);
            JTextField contatoField = new JTextField(leitor.getContato(), 25);

            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
            formPanel.add(new JLabel("Nome:"));
            formPanel.add(nomeField);
            formPanel.add(new JLabel("Data Nascimento (dd/mm/aaaa):"));
            formPanel.add(dataNascField);
            formPanel.add(new JLabel("Contato:"));
            formPanel.add(contatoField);

            int result = JOptionPane.showConfirmDialog(this, formPanel,
                    "Editar Leitor (ID: " + id + ")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nome = nomeField.getText();
                LocalDate dataNasc = LocalDate.parse(dataNascField.getText(), formatter);
                String contato = contatoField.getText();

                controller.alterarLeitor(id, nome, dataNasc, contato);
                JOptionPane.showMessageDialog(this, "Leitor alterado com sucesso!");
                atualizarTabela();
            }
        } catch (Excecao | DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar leitor: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirLeitor() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um leitor na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nome = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o leitor: " + nome + " (ID: " + id + ")?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.excluirLeitor(id);
                JOptionPane.showMessageDialog(this, "Leitor excluído com sucesso!");
                atualizarTabela();
            } catch (Excecao ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro de Regra de Negócio",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void quitarMulta() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um leitor na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Leitor leitor = controller.getLeitorPorId(id);
            if (!leitor.temPendencia()) {
                JOptionPane.showMessageDialog(this, "O leitor " + leitor.getNome() + " não possui multas pendentes.",
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String valorAtual = String.format("%.2f", leitor.getValorMultaPendente());
            String valorPagoStr = JOptionPane.showInputDialog(this,
                    "Leitor: " + leitor.getNome() + "\nMulta Atual: R$" + valorAtual + "\n\nDigite o valor a ser pago:",
                    "Quitar Multa", JOptionPane.PLAIN_MESSAGE);

            if (valorPagoStr != null && !valorPagoStr.trim().isEmpty()) {
                double valorPago = Double.parseDouble(valorPagoStr.replace(",", "."));
                Leitor leitorAtualizado = controller.quitarMulta(id, valorPago);

                String novoValor = String.format("%.2f", leitorAtualizado.getValorMultaPendente());
                JOptionPane.showMessageDialog(this, "Pagamento recebido! \nNovo saldo devedor: R$" + novoValor);
                atualizarTabela();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido. Digite apenas números (ex: 10.50).", "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Excecao ex) {
            JOptionPane.showMessageDialog(this, "Erro ao quitar multa: " + ex.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}