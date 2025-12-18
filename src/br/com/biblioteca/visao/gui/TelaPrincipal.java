package br.com.biblioteca.visao.gui;

import br.com.biblioteca.controlador.BibliotecaController;
import java.awt.BorderLayout;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal(BibliotecaController controller) {
        setTitle("Sistema de Gerenciamento de Biblioteca");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        abas.addTab("Operações", new PainelOperacoes(controller));

        abas.addTab("Livros", new PainelGerenciamentoLivros(controller));

        abas.addTab("Autores", new PainelGerenciamentoAutores(controller));

        abas.addTab("Leitores", new PainelGerenciamentoLeitores(controller));

        abas.addTab("Gêneros", new PainelGerenciamentoGeneros(controller));

        add(abas, BorderLayout.CENTER);
    }
}