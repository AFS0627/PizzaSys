package Model;

public class Pizza {
	private int id;
	private String nome;
	private String tamanho;
	private double preco;
	private String ingredientes;

	public Pizza(int id, String nome, String tamanho, double preco, String ingredientes) {
		this.id = id;
		this.nome = nome;
		this.tamanho = tamanho;
		this.preco = preco;
		this.ingredientes = ingredientes;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getTamanho() {
		return tamanho;
	}

	public double getPreco() {
		return preco;
	}

	public String getIngredientes() {
		return ingredientes;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}
}
