package Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Model.Funcionario;
import Model.ItemPedido;
import Model.Pedido;
import Model.Pizza;

public class PedidoController {
	private static List<Pedido> pedidos = new ArrayList<>();
	private static int proximoId = 1;
	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	 public static Pedido criarPedido(Funcionario funcionario) {
		if (funcionario == null) {
			return null;
		}
		String data = LocalDateTime.now().format(FORMATO_DATA);
		Pedido pedido = new Pedido(proximoId++, data, funcionario);
		pedidos.add(pedido);
		return pedido;
	}

	 public static List<Pedido> getPedidos() {
		return pedidos;
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

	 public static String adicionarItem(Pedido pedido, Pizza pizza, int quantidade,
			int tamanho, String observacao) {
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
		if (!"Pendente".equals(pedido.getStatus())) {
			return "Este pedido já foi finalizado.";
		}
		double preco = calcularPrecoTamanho(pizza, tamanho);
		if (observacao == null) {
			observacao = "";
		}
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
		pedido.removerItem(item);
		return null;
	}

	public static String finalizarPedido(Pedido pedido) {
		if (pedido == null) {
			return "Pedido inválido.";
		}
		if (!"Pendente".equals(pedido.getStatus())) {
			return "Este pedido já foi finalizado.";
		}
		if (pedido.getItens().isEmpty()) {
			return "Adicione pelo menos uma pizza ao pedido.";
		}
		pedido.setStatus("Finalizado");
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
}