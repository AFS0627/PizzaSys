package Model;

public class ItemPedido {
	private Pizza pizza;
	private int quantidade;
	private double preco;

	public ItemPedido(Pizza pizza, int quantidade) {
		this.pizza = pizza;
		this.quantidade = quantidade;
		this.preco = pizza.getPreco();
	}

	public Pizza getPizza() {
		return pizza;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public double getPreco() {
		return preco;
	}

	public double getSubtotal() {
		return preco * quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
