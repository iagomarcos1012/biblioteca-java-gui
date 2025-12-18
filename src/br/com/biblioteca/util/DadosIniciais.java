package br.com.biblioteca.util;

import br.com.biblioteca.modelo.*;
import br.com.biblioteca.persistencia.BancoDeDados;
import br.com.biblioteca.persistencia.Excecao;
import java.time.LocalDate;

public class DadosIniciais {
	public static void popularBanco(BancoDeDados banco) throws Excecao {
		System.out.println("Populando banco de dados com dados iniciais...");

		Autor autor1 = new Autor(
				"Autor britânico conhecido por suas obras distópicas",
				GeradorDeId.gerarId("Autor"),
				"George Orwell",
				LocalDate.of(1903, 6, 25),
				"geo@rwell.com");
		Autor autor2 = new Autor(
				"Autora britânica famosa pela série Harry Potter",
				GeradorDeId.gerarId("Autor"),
				"J.K. Rowling",
				LocalDate.of(1965, 7, 31),
				"jk@rowling.com");

		banco.getAutores().inserirEntidade(autor1);
		banco.getAutores().inserirEntidade(autor2);

		Genero genero1 = new Genero(GeradorDeId.gerarId("Genero"), "Distopia");
		Genero genero2 = new Genero(GeradorDeId.gerarId("Genero"), "Fantasia");

		banco.getGeneros().inserirEntidade(genero1);
		banco.getGeneros().inserirEntidade(genero2);

		Livro livro1 = new Livro(
				GeradorDeId.gerarId("Livro"),
				"1984",
				autor1,
				genero1,
				3);

		livro1.setDataPublicacao(LocalDate.of(1949, 6, 8));

		Livro livro2 = new Livro(
				GeradorDeId.gerarId("Livro"),
				"Harry Potter e a Pedra Filosofal",
				autor2,
				genero2,
				5);
		livro2.setDataPublicacao(LocalDate.of(1997, 6, 26));

		banco.getLivros().inserirEntidade(livro1);
		banco.getLivros().inserirEntidade(livro2);

		autor1.setLivrosEscritos(livro1);
		autor2.setLivrosEscritos(livro2);

		genero1.setLivrosDoGenero(livro1);
		genero2.setLivrosDoGenero(livro2);

		Leitor leitor1 = new Leitor(
				0.0,
				GeradorDeId.gerarId("Leitor"),
				"Alice",
				LocalDate.of(1990, 1, 1),
				"alice@email.com");
		Leitor leitor2 = new Leitor(
				0.0,
				GeradorDeId.gerarId("Leitor"),
				"Bob",
				LocalDate.of(1992, 2, 2),
				"bob@email.com");

		banco.getLeitores().inserirEntidade(leitor1);
		banco.getLeitores().inserirEntidade(leitor2);

		System.out.println("Dados iniciais carregados.");
	}
}
