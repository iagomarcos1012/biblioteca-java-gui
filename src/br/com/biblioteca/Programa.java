package br.com.biblioteca;

import br.com.biblioteca.controlador.BibliotecaController;
import br.com.biblioteca.persistencia.BancoDeDados;
import br.com.biblioteca.persistencia.Excecao;
import br.com.biblioteca.util.DadosIniciais;
import br.com.biblioteca.visao.gui.TelaPrincipal;
import javax.swing.SwingUtilities;

public class Programa {

    public static void main(String[] args) throws Excecao {
        BancoDeDados banco = new BancoDeDados();
        DadosIniciais.popularBanco(banco);
        BibliotecaController controller = new BibliotecaController(banco);

        SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal(controller);
            telaPrincipal.setVisible(true);
        });
    }
}