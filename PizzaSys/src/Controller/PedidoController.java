package Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.PedidoDAO;
import Model.Funcionario;
import Model.ItemPedido;
import Model.Pedido;
import Model.Pizza;

public class PedidoController {

	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	public static Pedido criarPedido(Funcionario funcionario) {

		if (funcionario == null) {
			return null;
		}

		String data = LocalDateTime.now().format(FORMATO_DATA);

		return new Pedido(0, data, funcionario);
	}

	public static String salvarPedido(Pedido pedido) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (pedido.getItens().isEmpty()) {
			return "Adicione pelo menos uma pizza ao pedido.";
		}

		if (pedido.getFuncionario() == null) {
			return "Funcionário não informado.";
		}

		try {
			pedido.setStatus("Pendente");

			int id = PedidoDAO.inserir(pedido);

			pedido.setId(id);

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return "Não foi possível salvar o pedido no banco de dados.";
		}
	}

	public static List<Pedido> getPedidos() {

		try {
			return PedidoDAO.buscarTodos();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Pedido>();
		}
	}

	public static List<Pedido> getPedidosPendentes() {

		try {
			return PedidoDAO.buscarPorStatus("Pendente");

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Pedido>();
		}
	}

	public static List<Pedido> getPedidosConcluidos() {

		try {
			return PedidoDAO.buscarPorStatus("Concluído");

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Pedido>();
		}
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

		try {
			PedidoDAO.atualizarStatus(pedido);

			return null;

		} catch (Exception e) {
			e.printStackTrace();

			pedido.setStatus("Pendente");

			return "Não foi possível iniciar a edição do pedido.";
		}
	}

	public static String salvarEdicao(Pedido pedido) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (pedido.getId() <= 0) {
			return "Pedido ainda não foi salvo.";
		}

		if (!"Em andamento".equals(pedido.getStatus())) {
			return "Este pedido não está em edição.";
		}

		if (pedido.getItens().isEmpty()) {
			return "Adicione pelo menos uma pizza ao pedido.";
		}

		pedido.setStatus("Pendente");

		try {
			PedidoDAO.atualizarPedido(pedido);

			return null;

		} catch (Exception e) {
			e.printStackTrace();

			pedido.setStatus("Em andamento");

			return "Não foi possível salvar as alterações do pedido.";
		}
	}

	public static String cancelarEdicao(Pedido pedido) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (pedido.getId() <= 0) {
			return "Pedido ainda não foi salvo.";
		}

		if (!"Em andamento".equals(pedido.getStatus())) {
			return "Este pedido não está em edição.";
		}

		if (pedido.getItens().isEmpty()) {
			return "O pedido não possui itens.";
		}

		pedido.setStatus("Pendente");

		try {
			PedidoDAO.atualizarStatus(pedido);

			return null;

		} catch (Exception e) {
			e.printStackTrace();

			pedido.setStatus("Em andamento");

			return "Não foi possível cancelar a edição.";
		}
	}

	public static String finalizarPedido(Pedido pedido, int formaPagamento) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (pedido.getId() <= 0) {
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

		try {
			PedidoDAO.finalizarPedido(pedido, formaPagamento);

			return null;

		} catch (Exception e) {
			e.printStackTrace();

			pedido.setStatus("Pendente");

			return "Não foi possível concluir o pedido.";
		}
	}

	public static String excluirPedido(Pedido pedido) {

		if (pedido == null) {
			return "Pedido inválido.";
		}

		if (pedido.getId() <= 0) {
			return "Pedido não encontrado.";
		}

		if ("Concluído".equals(pedido.getStatus())) {
			return "Pedidos concluídos não podem ser excluídos.";
		}

		try {
			PedidoDAO.excluir(pedido.getId());

			return null;

		} catch (Exception e) {
			e.printStackTrace();

			return "Não foi possível excluir o pedido.";
		}
	}

	public static double calcularTotal(Pedido pedido) {

		if (pedido == null) {
			return 0;
		}

		return pedido.getValorTotal();
	}

	public static Pedido buscarPedido(int id) {

		if (id <= 0) {
			return null;
		}

		try {
			return PedidoDAO.buscarPorId(id);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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