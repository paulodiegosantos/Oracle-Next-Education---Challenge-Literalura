package br.com.challenger.literalura;

import br.com.challenger.literalura.client.ApiService;
import br.com.challenger.literalura.client.dto.Book;
import br.com.challenger.literalura.client.dto.BookResponse;
import br.com.challenger.literalura.service.LibraryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final Scanner scanner = new Scanner(System.in);
	private final ApiService apiService = new ApiService();
	private final LibraryService libraryService;

	public LiteraluraApplication(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	// --- Buscar livro pelo título e salvar no banco ---
	private void buscarLivroPorTitulo() {

		System.out.print("Digite o título do livro: ");
		String titulo = scanner.nextLine();

		BookResponse response = apiService.fetchBooksAsObject(titulo);

		if (response.getResults().isEmpty()) {
			System.out.println("Nenhum livro encontrado com o título \"" + titulo + "\".");
			return;
		}

		Book livro = response.getResults().get(0);

		// Considera apenas o primeiro autor e idioma
		if (!livro.getAuthors().isEmpty()) {
			livro.setAuthors(List.of(livro.getAuthors().get(0)));
		}
		if (!livro.getLanguages().isEmpty()) {
			livro.setLanguages(List.of(livro.getLanguages().get(0)));
		}

		// Verifica se o livro já existe antes de salvar
		boolean existe = libraryService.existeLivro(
				livro.getTitle(),
				livro.getAuthors().isEmpty() ? null : livro.getAuthors().get(0).getName()
		);

		if (existe) {
			System.out.println("\nLivro já cadastrado no banco, não será duplicado:");
		} else {
			libraryService.salvarLivroDTO(livro);
			System.out.println("\nLivro encontrado e registrado no banco:");
		}

		System.out.println(
				livro.getTitle() +
						" | Autor(es): " + livro.getAuthorsNames() +
						" | Idioma: " + (livro.getLanguages().isEmpty() ? "Desconhecido" : livro.getLanguages().get(0)) +
						" | Downloads: " + livro.getDownloadCount()
		);
	}

	// --- Delegar para o Service (que está @Transactional) ---
	private void listarLivrosRegistrados() {
		libraryService.listarLivrosRegistrados();
	}

	private void listarAutoresRegistrados() {
		libraryService.listarAutoresRegistrados();
	}

	private void listarAutoresVivosEmAno() {
		System.out.print("Digite o ano para consultar autores vivos: ");
		try {
			int ano = Integer.parseInt(scanner.nextLine());
			libraryService.listarAutoresVivosEmAno(ano);
		} catch (NumberFormatException e) {
			System.out.println("Ano inválido!");
		}
	}

	// --- Exibir quantidade de livros por idioma ---
	private void exibirQuantidadeLivrosPorIdioma() {
		String opcoesIdiomas = """
			es - espanhol
			en - inglês
			fr - francês
			pt - português

			Digite o idioma para consultar:\s""";
		System.out.print(opcoesIdiomas);
		String idioma = scanner.nextLine();

		if (idioma == null || idioma.isBlank()) {
			System.out.println("Idioma inválido!");
			return;
		}

		libraryService.exibirQuantidadeLivrosPorIdioma(idioma);
	}

	// --- Menu ---
	private void exibirMenu() {

		String menu = """
                Escolha o número de sua opção:
                1- buscar livro pelo título
                2- listar livros registrados
                3- listar autores registrados
                4- listar autores vivos em um determinado ano
                5- listar livros em um determinado idioma
                0 - sair
                """;

		int opcao;

		do {
			System.out.println(menu);
			System.out.print("Opção: ");

			try {
				opcao = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Opção inválida! Digite um número.");
				opcao = -1;
				continue;
			}

			switch (opcao) {
				case 1 -> buscarLivroPorTitulo();
				case 2 -> listarLivrosRegistrados();
				case 3 -> listarAutoresRegistrados();
				case 4 -> listarAutoresVivosEmAno();
				case 5 -> exibirQuantidadeLivrosPorIdioma();
				case 0 -> System.out.println("Saindo da aplicação.");
				default -> System.out.println("Opção inválida! Tente novamente.");
			}

		} while (opcao != 0);
	}

	@Override
	public void run(String... args) {
		exibirMenu();
		scanner.close();
	}
}