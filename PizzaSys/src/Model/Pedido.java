package Model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private int id;
	private String data;
	private String status;
	private Funcionario funcionario;
	private List<ItemPedido> itens;

	public Pedido(int id, String data, Funcionario funcionario) {
		this.id = id;
		this.data = data;
		this.funcionario = funcionario;
		this.status = "Pendente";
		this.itens = new ArrayList<>();
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

	public void adicionarItem(ItemPedido item) {
		itens.add(item);
	}

	public void removerItem(ItemPedido item) {
		itens.remove(item);
	}

	public double getValorTotal() {
		double total = 0;

		for (ItemPedido item : itens) {
			total += item.getSubtotal();
		}

		return total;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
