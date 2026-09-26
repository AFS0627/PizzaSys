package Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Model.Funcionario;
import Model.ItemPedido;
import Model.Pedido;
import Model.Pizza;

public class PedidoController {

	private static final List<Pedido> pedidos = new ArrayList<>();

	private static int proximoId = 1;

	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	public static Pedido criarPedido(Funcionario funcionario) {

		if (funcionario == null) {
			return null;
		}

		String data = LocalDateTime.now().format(FORMATO_DATA);

		return new Pedido(proximoId++, data, funcionario);
	}

	public static String salvarPedido(Pedido pedido) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (pedido.getItens().isEmpty()) {
			return "Adicione pelo menos uma pizza ao pedido.";
		}

		if (pedidos.contains(pedido)) {
			return "Este pedido já foi salvo.";
		}

		pedido.setStatus("Pendente");
		pedidos.add(pedido);

		return null;
	}

	public static List<Pedido> getPedidos() {
		return new ArrayList<>(pedidos);
	}

	public static List<Pedido> getPedidosPendentes() {

		List<Pedido> pendentes = new ArrayList<>();

		for (Pedido pedido : pedidos) {

			if ("Pendente".equals(pedido.getStatus())) {
				pendentes.add(pedido);
			}
		}

		pendentes.sort(Comparator.comparingInt(Pedido::getId));

		return pendentes;
	}

	public static List<Pedido> getPedidosConcluidos() {

		List<Pedido> concluidos = new ArrayList<>();

		for (Pedido pedido : pedidos) {

			if ("Concluído".equals(pedido.getStatus())) {
				concluidos.add(pedido);
			}
		}

		concluidos.sort(Comparator.comparingInt(Pedido::getId));

		return concluidos;
	}

	public static double calcularPrecoTamanho(Pizza pizza, int tamanho) {

		if (pizza == null) {
			return 0;
		}

		double preco = pizza.getPreco();

		switch (tamanho) {

		case 1:
			return preco * 0.80;

		case 2:
			return preco;

		case 3:
			return preco * 1.20;

		case 4:
			return preco * 1.40;

		default:
			return preco;
		}
	}

	public static String nomeTamanho(int tamanho) {

		switch (tamanho) {

		case 1:
			return "Pequeno";

		case 2:
			return "Médio";

		case 3:
			return "Grande";

		case 4:
			return "Extra Grande";

		default:
			return "Desconhecido";
		}
	}

	public static String adicionarItem(Pedido pedido, Pizza pizza, int quantidade, int tamanho, String observacao) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (pizza == null) {
			return "Selecione uma pizza.";
		}

		if (quantidade <= 0) {
			return "A quantidade deve ser maior que zero.";
		}

		if (tamanho < 1 || tamanho > 4) {
			return "Selecione um tamanho válido.";
		}

		if ("Concluído".equals(pedido.getStatus())) {
			return "Este pedido já foi concluído.";
		}

		if (observacao == null) {
			observacao = "";
		}

		double preco = calcularPrecoTamanho(pizza, tamanho);

		ItemPedido item = new ItemPedido(pizza, quantidade, preco, observacao.trim(), tamanho);

		pedido.adicionarItem(item);

		return null;
	}

	public static String removerItem(Pedido pedido, ItemPedido item) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (item == null) {
			return "Item inválido.";
		}

		if (!pedido.getItens().contains(item)) {
			return "Item não encontrado no pedido.";
		}

		if ("Concluído".equals(pedido.getStatus())) {
			return "Este pedido já foi concluído.";
		}

		pedido.removerItem(item);

		return null;
	}

	public static String iniciarEdicao(Pedido pedido) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (!"Pendente".equals(pedido.getStatus())) {
			return "Somente pedidos pendentes podem ser editados.";
		}

		pedido.setStatus("Em andamento");

		return null;
	}

	public static String salvarEdicao(Pedido pedido) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (!"Em andamento".equals(pedido.getStatus())) {
			return "Este pedido não está em edição.";
		}

		if (pedido.getItens().isEmpty()) {
			return "Adicione pelo menos uma pizza ao pedido.";
		}

		pedido.setStatus("Pendente");

		return null;
	}

	public static String cancelarEdicao(Pedido pedido) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (!"Em andamento".equals(pedido.getStatus())) {
			return "Este pedido não está em edição.";
		}

		if (pedido.getItens().isEmpty()) {
			return "O pedido não possui itens.";
		}

		pedido.setStatus("Pendente");

		return null;
	}

	public static String finalizarPedido(Pedido pedido, int formaPagamento) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (!pedidos.contains(pedido)) {
			return "Este pedido ainda não foi salvo.";
		}

		if (!"Pendente".equals(pedido.getStatus())) {
			return "Somente pedidos pendentes podem ser concluídos.";
		}

		if (pedido.getItens().isEmpty()) {
			return "Adicione pelo menos uma pizza ao pedido.";
		}

		if (formaPagamento < 1 || formaPagamento > 4) {

			return "Forma de pagamento inválida.";
		}

		pedido.setFormaPagamento(formaPagamento);
		pedido.setStatus("Concluído");

		return null;
	}

	public static String excluirPedido(Pedido pedido) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (!pedidos.contains(pedido)) {
			return "Pedido não encontrado.";
		}

		if ("Concluído".equals(pedido.getStatus())) {
			return "Pedidos concluídos não podem ser excluídos.";
		}

		pedidos.remove(pedido);

		return null;
	}

	public static double calcularTotal(Pedido pedido) {

		if (pedido == null) {
			return 0;
		}

		return pedido.getValorTotal();
	}

	public static Pedido buscarPedido(int id) {

		for (Pedido pedido : pedidos) {

			if (pedido.getId() == id) {
				return pedido;
			}
		}

		return null;
	}

	public static String nomeFormaPagamento(int formaPagamento) {

		switch (formaPagamento) {

		case 1:
			return "Dinheiro";

		case 2:
			return "Pix";

		case 3:
			return "Débito";

		case 4:
			return "Crédito";

		default:
			return "Não informado";
		}
	}
}