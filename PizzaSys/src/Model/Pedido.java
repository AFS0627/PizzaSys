package Model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

	private int id;
	private String data;
	private String status;
	private Funcionario funcionario;
	private List<ItemPedido> itens;
	private int formaPagamento;

	public Pedido(int id, String data, Funcionario funcionario) {
		this.id = id;
		this.data = data;
		this.funcionario = funcionario;
		this.status = "Pendente";
		this.itens = new ArrayList<>();
		this.formaPagamento = 0;
	}

	public int getId() {
		return id;
	}

	public String getData() {
		return data;
	}

	public String getStatus() {
		return status;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public int getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(int formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void adicionarItem(ItemPedido item) {
		if (item != null) {
			itens.add(item);
		}
	}

	public void removerItem(ItemPedido item) {
		itens.remove(item);
	}

	public double getValorTotal() {
		double total = 0;

		for (ItemPedido item : itens) {
			if (item != null) {
				total += item.getSubtotal();
			}
		}

		return total;
	}
}