package br.com.biblioteca.testes; // Agora faz parte do pacote

import br.com.biblioteca.modelo.Genero;
import br.com.biblioteca.persistencia.Persistente;
import br.com.biblioteca.persistencia.Excecao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestePersistente {

    private Persistente<Genero> banco;
    private Genero genero1;
    private Genero genero2;

    @Before
    public void setUp() {
        banco = new Persistente<>();
        genero1 = new Genero(1, "Ficção Científica");
        genero2 = new Genero(2, "Fantasia");
    }

    // --- TESTES DE INSERIR ---

    @Test
    public void testInserirIdNaoExistente() {
        try {
            banco.inserirEntidade(genero1);
            Genero recuperado = banco.getEntidadeById(1);
            assertEquals("Ficção Científica", recuperado.getGenero());
        } catch (Excecao e) {
            fail("Erro ao inserir ID novo: " + e.getMessage());
        }
    }

    @Test
    public void testInserirIdJaExistente() {
        try {
            banco.inserirEntidade(genero1);
        } catch (Excecao e) {
            fail("Erro na preparação.");
        }

        Genero duplicado = new Genero(1, "Terror");
        try {
            banco.inserirEntidade(duplicado);
            fail("Deveria ter lançado Excecao.");
        } catch (Excecao e) {
            assertTrue(e.getMessage().contains("Já existe"));
        }
    }

    // --- TESTES DE ALTERAR ---

    @Test
    public void testAlterarIdNaoExistente() {
        Genero inexistente = new Genero(99, "Drama");
        try {
            banco.alterarEntidade(inexistente);
            fail("Deveria ter lançado Excecao.");
        } catch (Excecao e) {
            // Sucesso
        }
    }

    @Test
    public void testAlterarIdExistente() {
        try {
            banco.inserirEntidade(genero1);
            Genero modificado = new Genero(1, "Sci-Fi");
            banco.alterarEntidade(modificado);

            Genero g = banco.getEntidadeById(1);
            assertEquals("Sci-Fi", g.getGenero());
        } catch (Excecao e) {
            fail("Erro ao alterar: " + e.getMessage());
        }
    }

    // --- TESTES DE APAGAR ---

    @Test
    public void testApagarIdNaoExistente() {
        try {
            banco.excluirEntidade(99);
            fail("Deveria ter lançado Excecao.");
        } catch (Excecao e) {
            // Sucesso
        }
    }

    @Test
    public void testApagarIdJaExistente() {
        try {
            banco.inserirEntidade(genero1);
            banco.excluirEntidade(1);

            try {
                banco.getEntidadeById(1);
                fail("O ID 1 deveria ter sumido.");
            } catch (Excecao e) {
                // Sucesso
            }
        } catch (Excecao e) {
            fail("Erro ao apagar.");
        }
    }

    // --- TESTES DE BUSCAR ---

    @Test
    public void testBuscarIdNaoExistente() {
        try {
            banco.getEntidadeById(50);
            fail("Deveria ter lançado Excecao.");
        } catch (Excecao e) {
            // Sucesso
        }
    }

    @Test
    public void testBuscarIdExistente() {
        try {
            banco.inserirEntidade(genero2);
            Genero g = banco.getEntidadeById(2);
            assertNotNull(g);
            assertEquals("Fantasia", g.getGenero());
        } catch (Excecao e) {
            fail("Erro ao buscar.");
        }
    }
}