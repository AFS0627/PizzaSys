package Controller;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.Pizza;
import View.TelaPizzas;

public class PizzaController {

	private static List<Pizza> pizzas = new ArrayList<>();
	private static int proximoId = 1;

	public static List<Pizza> getPizzas() {
		return pizzas;
	}

	public static void adicionarPizza(TelaPizzas tela, String nome, String precoTexto, String descricao) {

		if (nome.trim().isEmpty()) {
			tela.mostrarErro("Digite o nome da pizza.");
			return;
		}

		if (precoTexto.trim().isEmpty()) {
			tela.mostrarErro("Digite o preço.");
			return;
		}

		if (descricao.trim().isEmpty()) {
			tela.mostrarErro("Digite a descrição.");
			return;
		}

		double preco;

		try {
			preco = Double.parseDouble(precoTexto.replace(",", "."));
		} catch (NumberFormatException e) {
			tela.mostrarErro("Digite um preço válido.");
			return;
		}

		File arquivoImagem = selecionarImagem(tela);

		if (arquivoImagem == null) {
			return;
		}

		int id = proximoId++;

		String caminhoImagem = copiarImagem(arquivoImagem, id);

		if (caminhoImagem == null) {
			tela.mostrarErro("Não foi possível salvar a imagem.");
			return;
		}

		Pizza pizza = new Pizza(id, nome, preco, descricao, caminhoImagem);

		pizzas.add(pizza);

		tela.limparErro();
		tela.atualizarFeed();
	}

	public static void editarPizza(TelaPizzas tela, Pizza pizza, String nome, String precoTexto, String descricao) {

		if (pizza == null) {
			JOptionPane.showMessageDialog(tela, "Selecione uma pizza para editar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (nome.trim().isEmpty()) {
			tela.mostrarErro("Digite o nome da pizza.");
			return;
		}

		if (precoTexto.trim().isEmpty()) {
			tela.mostrarErro("Digite o preço.");
			return;
		}

		if (descricao.trim().isEmpty()) {
			tela.mostrarErro("Digite a descrição.");
			return;
		}

		double preco;

		try {
			preco = Double.parseDouble(precoTexto.replace(",", "."));
		} catch (NumberFormatException e) {
			tela.mostrarErro("Digite um preço válido.");
			return;
		}

		pizza.setNome(nome);
		pizza.setPreco(preco);
		pizza.setDescricao(descricao);

		int resposta = JOptionPane.showConfirmDialog(tela, "Deseja alterar a imagem da pizza?", "Imagem",
				JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {

			File arquivoImagem = selecionarImagem(tela);

			if (arquivoImagem != null) {

				String imagemAntiga = pizza.getImagem();

				String novaImagem = copiarImagem(arquivoImagem, pizza.getId());

				if (novaImagem == null) {
					tela.mostrarErro("Não foi possível salvar a nova imagem.");
					return;
				}

				apagarImagemAntiga(imagemAntiga, novaImagem);

				pizza.setImagem(novaImagem);
			}
		}

		tela.limparErro();
		tela.atualizarFeed();

		JOptionPane.showMessageDialog(tela, "Pizza alterada com sucesso!");
	}

	public static void excluirPizza(Component parent, Pizza pizza, TelaPizzas tela) {

		if (pizza == null) {
			JOptionPane.showMessageDialog(parent, "Selecione uma pizza para excluir.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int resposta = JOptionPane.showConfirmDialog(parent,
				"Tem certeza que deseja excluir a pizza " + pizza.getNome() + "?", "Excluir pizza",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (resposta != JOptionPane.YES_OPTION) {
			return;
		}

		apagarImagem(pizza.getImagem());

		pizzas.remove(pizza);

		tela.setPizzaSelecionada(null);
		tela.atualizarFeed();

		JOptionPane.showMessageDialog(parent, "Pizza excluída com sucesso!");
	}

	public static void selecionarImagemParaAdicionar(TelaPizzas tela, String nome, String preco, String descricao) {

		adicionarPizza(tela, nome, preco, descricao);
	}

	public static File selecionarImagem(Component parent) {

		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setDialogTitle("Selecionar imagem da pizza");

		fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "gif", "bmp"));

		int resultado = fileChooser.showOpenDialog(parent);

		if (resultado == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}

		return null;
	}

	private static String copiarImagem(File arquivoOrigem, int id) {

		try {

			File pastaImagens = new File("imagens");

			if (!pastaImagens.exists()) {
				pastaImagens.mkdirs();
			}

			String extensao = obterExtensao(arquivoOrigem.getName());

			removerImagensDoId(id);

			File arquivoDestino = new File(pastaImagens, "pizza_" + id + extensao);

			Files.copy(arquivoOrigem.toPath(), arquivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

			return "imagens/" + arquivoDestino.getName();

		} catch (IOException e) {
			return null;
		}
	}

	private static void removerImagensDoId(int id) {

		File pastaImagens = new File("imagens");

		if (!pastaImagens.exists()) {
			return;
		}

		File[] arquivos = pastaImagens.listFiles();

		if (arquivos == null) {
			return;
		}

		String prefixo = "pizza_" + id + ".";

		for (File arquivo : arquivos) {

			if (arquivo.isFile() && arquivo.getName().startsWith(prefixo)) {

				arquivo.delete();
			}
		}
	}

	private static void apagarImagemAntiga(String imagemAntiga, String novaImagem) {

		if (imagemAntiga == null || imagemAntiga.trim().isEmpty()) {
			return;
		}

		if (imagemAntiga.equals(novaImagem)) {
			return;
		}

		apagarImagem(imagemAntiga);
	}

	private static void apagarImagem(String caminho) {

		if (caminho == null || caminho.trim().isEmpty()) {
			return;
		}

		File arquivo = new File(caminho);

		if (arquivo.exists()) {
			arquivo.delete();
		}
	}

	private static String obterExtensao(String nomeArquivo) {

		int ponto = nomeArquivo.lastIndexOf(".");

		if (ponto >= 0) {
			return nomeArquivo.substring(ponto);
		}

		return ".png";
	}
}