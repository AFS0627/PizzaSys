package Model;

public class ItemPedido {
	private Pizza pizza;
	private int quantidade;
	private double preco;
	private String observacao;
	private int tamanho;

	public ItemPedido(Pizza pizza, int quantidade, double preco, String observacao, int tamanho) {
		this.pizza = pizza;
		this.quantidade = quantidade;
		this.preco = preco;
		this.observacao = observacao;
		this.tamanho = tamanho;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public void setPreco(double preco) {
		this.preco = preco;
	}
}