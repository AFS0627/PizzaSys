package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.RelatorioDAO;
import Model.Funcionario;
import Model.Pedido;
import Model.Relatorio;

public class RelatoriosController {

	public static void registrarMovimentacao(Pedido pedido, String acao) {
		if (pedido == null) {
			return;
		}

		registrarMovimentacao(pedido.getFuncionario(), acao, pedido.getId());
	}

	public static void registrarMovimentacao(Funcionario funcionario, String acao, int idPedido) {

		try {
			Relatorio relatorio = new Relatorio(funcionario, acao, idPedido);

			RelatorioDAO.inserir(relatorio);

		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível registrar a movimentação.", e);
		}
	}

	public static List<Relatorio> getMovimentacoes() {
		try {
			return RelatorioDAO.buscarTodos();
		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível carregar as movimentações.", e);
		}
	}

	public static List<Relatorio> getMovimentacoesFuncionario(
	        Funcionario funcionario) {

	    if (funcionario == null) {
	        return new ArrayList<>();
	    }

	    try {
	        return RelatorioDAO.buscarPorFuncionario(
	                funcionario.getId()
	        );
	    } catch (SQLException e) {
	        throw new RuntimeException(
	                "Não foi possível carregar as movimentações do funcionário.",
	                e
	        );
	    }
	}

	public static double getFaturamento() {
		double total = 0;

		for (Pedido pedido : PedidoController.getPedidosConcluidos()) {
			total += pedido.getValorTotal();
		}

		return total;
	}

	public static int getQuantidadePedidos() {
		return PedidoController.getPedidosConcluidos().size();
	}

	public static int getQuantidadePagamentos(int formaPagamento) {
		int quantidade = 0;

		for (Pedido pedido : PedidoController.getPedidosConcluidos()) {
			if (pedido.getFormaPagamento() == formaPagamento) {
				quantidade++;
			}
		}

		return quantidade;
	}

	public static double getFaturamentoPagamento(int formaPagamento) {
		double total = 0;

		for (Pedido pedido : PedidoController.getPedidosConcluidos()) {
			if (pedido.getFormaPagamento() == formaPagamento) {
				total += pedido.getValorTotal();
			}
		}

		return total;
	}

	public static int getQuantidadeDinheiro() {
		return getQuantidadePagamentos(1);
	}

	public static int getQuantidadePix() {
		return getQuantidadePagamentos(2);
	}

	public static int getQuantidadeDebito() {
		return getQuantidadePagamentos(3);
	}

	public static int getQuantidadeCredito() {
		return getQuantidadePagamentos(4);
	}

	public static double getFaturamentoDinheiro() {
		return getFaturamentoPagamento(1);
	}

	public static double getFaturamentoPix() {
		return getFaturamentoPagamento(2);
	}

	public static double getFaturamentoDebito() {
		return getFaturamentoPagamento(3);
	}

	public static double getFaturamentoCredito() {
		return getFaturamentoPagamento(4);
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